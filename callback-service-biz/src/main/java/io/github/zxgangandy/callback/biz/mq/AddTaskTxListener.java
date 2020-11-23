package io.github.zxgangandy.callback.biz.mq;

import io.andy.rocketmq.wrapper.core.producer.LocalTxState;
import io.andy.rocketmq.wrapper.core.producer.listener.MQTxListener;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqBO;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqWrapperBO;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.commons.lang3.StringUtils.EMPTY;


public class AddTaskTxListener implements MQTxListener {
    @Autowired
    private ICallbackTaskService callbackTaskService;

    @Override
    public LocalTxState executeTransaction(Object req) {
        AddTaskReqWrapperBO wrapper = (AddTaskReqWrapperBO) req;
        CallbackTask callbackTask = createCallbackTask(wrapper);
        try {
            if (!callbackTaskService.save(callbackTask)) {
                return LocalTxState.ROLLBACK;
            }
        } catch (Exception ex) {
            return LocalTxState.UNKNOWN;
        }

        return LocalTxState.COMMIT;
    }

    @Override
    public LocalTxState checkTransaction(Object body) {

        return LocalTxState.COMMIT;
    }


    private static CallbackTask createCallbackTask(AddTaskReqWrapperBO wrapper) {
        AddTaskReqBO reqBO = wrapper.getReqBO();
        return new CallbackTask()
                .setTaskId(Long.parseLong(wrapper.getTaskId()))
                .setCallCount(0)
                .setCallSuccess(EMPTY)
                .setCallResult(EMPTY)
                .setCallExpect(reqBO.getCallExpect())
                .setBizType(reqBO.getBizType())
                .setReqMethod(reqBO.getReqMethod())
                .setReqParam(reqBO.getReqParam())
                .setSourceApp(reqBO.getSourceApp())
                .setSourceIp(reqBO.getSourceIp())
                .setTargetApp(reqBO.getTargetApp());
    }

}
