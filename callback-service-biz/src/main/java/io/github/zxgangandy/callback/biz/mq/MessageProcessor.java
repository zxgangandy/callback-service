package io.github.zxgangandy.callback.biz.mq;

import io.andy.rocketmq.wrapper.core.consumer.processor.ConcurrentlyProcessor;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqWrapperBO;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MessageProcessor implements ConcurrentlyProcessor<AddTaskReqWrapperBO> {

    @Autowired
    private ICallbackTaskService callbackTaskService;

    @Override
    public ConsumeConcurrentlyStatus process(AddTaskReqWrapperBO wrapper) {
        try {
            if (!processMessage(wrapper)) {
                log.warn("MessageProcessor=>need retry, taskId={}", wrapper.getTaskId());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        } catch (Exception ex) {
            log.error("processMessage failed, taskId={}, ex={}", wrapper.getTaskId(), ex);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private boolean processMessage(AddTaskReqWrapperBO reqBO) throws IOException {
        return callbackTaskService.execTask(reqBO);
    }

}
