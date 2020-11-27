package io.github.zxgangandy.callback.biz.converter;



import io.github.zxgangandy.callback.biz.bo.AddTaskReqBO;
import io.github.zxgangandy.callback.biz.bo.AddTaskReqWrapperBO;
import io.github.zxgangandy.callback.biz.bo.TaskListRespBO;
import io.github.zxgangandy.callback.biz.entity.CallbackLog;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface Wrapper2LogConverter extends BasicObjectMapper<AddTaskReqWrapperBO, CallbackLog> {

    default CallbackLog to(AddTaskReqWrapperBO source) {
        final AddTaskReqBO req = source.getReqBO();
        final long taskId = Long.parseLong(source.getTaskId());

        return new CallbackLog().setTaskId(taskId)
                .setBizType(req.getBizType())
                .setReqMethod(req.getReqMethod())
                .setReqParam(req.getReqParam())
                .setSourceApp(req.getSourceApp())
                .setSourceIp(req.getSourceIp())
                .setTargetApp(req.getTargetApp())
                .setTargetUrl(req.getTargetUrl());
    }
}
