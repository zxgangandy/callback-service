package io.github.zxgangandy.callback.biz.mq;

import io.andy.rocketmq.wrapper.core.consumer.processor.ConcurrentlyProcessor;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqWrapperBO;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static io.github.zxgangandy.callback.biz.constant.CallSuccessStatus.FAILED;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
@Slf4j
public class MessageProcessor implements ConcurrentlyProcessor<AddTaskReqWrapperBO> {

    @Autowired
    private ICallbackTaskService callbackTaskService;

    @Override
    public ConsumeConcurrentlyStatus process(AddTaskReqWrapperBO wrapper) {
        try {
            processMessage(wrapper);
        } catch (Exception ex) {
            log.error("processMessage failed, ex={}", ex);
            wrapper.setCallSuccess(FAILED.getStatus());
            wrapper.setCallResult(EMPTY);

            callbackTaskService.execFailedResult(wrapper);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private void processMessage(AddTaskReqWrapperBO reqBO) throws IOException {
        callbackTaskService.execTask(reqBO);
    }

}
