package io.github.zxgangandy.callback.biz.mq;

import io.andy.rocketmq.wrapper.core.consumer.processor.ConcurrentlyProcessor;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqBO;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqWrapperBO;
import io.github.zxgangandy.callback.biz.entity.CallbackLog;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import io.github.zxgangandy.callback.biz.service.ICallbackLogService;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import io.jingwei.base.utils.net.HttpClientUtil;
import io.jingwei.base.utils.tx.TxTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
@Slf4j
public class MessageProcessor implements ConcurrentlyProcessor {
    private static final String CALL_FAILED = "FAILED";
    private static final String CALL_SUCCESS = "SUCCESS";

    @Autowired
    private ICallbackTaskService callbackTaskService;

    @Autowired
    private ICallbackLogService  callbackLogService;

    @Autowired
    private TxTemplateService    txTemplateService;

    @Override
    public ConsumeConcurrentlyStatus process(Object messageBody) {
        AddTaskReqWrapperBO wrapper = (AddTaskReqWrapperBO) (messageBody);
        try {
            processMessage(wrapper);
        } catch (Exception ex) {
            log.error("processMessage failed, ex={}", ex);
            updateFailedResult(wrapper, EMPTY);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private void processMessage(AddTaskReqWrapperBO reqBO) throws IOException {
        final AddTaskReqBO req = reqBO.getReqBO();
        final String targetUrl = req.getTargetUrl();
        final String reqParam = req.getReqParam();

        String resp = HttpClientUtil.getInstance().postSync(targetUrl, reqParam);
        String callSuccess = CALL_FAILED;
        if (req.getCallExpect().contains(resp)) {
            callSuccess = CALL_SUCCESS;
        }

        updateSuccessResult(reqBO, resp, callSuccess);
    }

    private void updateSuccessResult(AddTaskReqWrapperBO wrapper, String callResult, String callSuccess) {
        txTemplateService.doInTransaction(() -> {
            updateCallResult(callSuccess, callResult, Long.parseLong(wrapper.getTaskId()));
            saveLog(wrapper, callResult);
        });
    }

    private void updateFailedResult(AddTaskReqWrapperBO wrapper, String callResult) {
        txTemplateService.doInTransaction(() -> {
            updateCallResult(CALL_FAILED, callResult, Long.parseLong(wrapper.getTaskId()));
            saveLog(wrapper, callResult);
        });
    }

    private void updateCallResult(String callSuccess, String resp, long taskId) {
        callbackTaskService.lambdaUpdate()
                .set(CallbackTask::getCallSuccess, callSuccess)
                .set(CallbackTask::getCallResult, resp)
                .setSql("call_count = call_count + 1")
                .eq(CallbackTask::getTaskId, taskId)
                .update();
    }

    private void saveLog(AddTaskReqWrapperBO wrapper, String callResult) {
        callbackLogService.save(createCallbackLog(wrapper, callResult));
    }

    private CallbackLog createCallbackLog(AddTaskReqWrapperBO wrapper, String callResult) {
        final AddTaskReqBO req = wrapper.getReqBO();
        final long taskId = Long.parseLong(wrapper.getTaskId());

        return new CallbackLog().setTaskId(taskId)
                .setBizType(req.getBizType())
                .setReqMethod(req.getReqMethod())
                .setReqParam(req.getReqParam())
                .setSourceApp(req.getSourceApp())
                .setSourceIp(req.getSourceIp())
                .setTargetApp(req.getTargetApp())
                .setTargetUrl(req.getTargetUrl())
                .setCallResult(callResult);
    }

}
