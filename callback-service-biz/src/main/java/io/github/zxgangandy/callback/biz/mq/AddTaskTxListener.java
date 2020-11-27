package io.github.zxgangandy.callback.biz.mq;

import io.andy.rocketmq.wrapper.core.producer.LocalTxState;
import io.andy.rocketmq.wrapper.core.producer.listener.MQTxListener;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqBO;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqWrapperBO;
import io.github.zxgangandy.callback.biz.converter.TaskAddReqConverter;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import org.springframework.beans.factory.annotation.Autowired;


public class AddTaskTxListener implements MQTxListener {
    @Autowired
    private ICallbackTaskService callbackTaskService;

    @Autowired
    private TaskAddReqConverter  taskAddReqConverter;

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
        AddTaskReqWrapperBO wrapper = (AddTaskReqWrapperBO) body;
        final long taskId = Long.parseLong(wrapper.getTaskId());

        if (!callbackTaskService.getByTaskId(taskId).isPresent()) {
            return LocalTxState.ROLLBACK;
        }

        return LocalTxState.COMMIT;
    }


    private CallbackTask createCallbackTask(AddTaskReqWrapperBO wrapper) {
        AddTaskReqBO reqBO = wrapper.getReqBO();

        CallbackTask callbackTask = taskAddReqConverter.to(reqBO);
        callbackTask.setTaskId(Long.parseLong(wrapper.getTaskId()));

        return callbackTask;
    }

}
