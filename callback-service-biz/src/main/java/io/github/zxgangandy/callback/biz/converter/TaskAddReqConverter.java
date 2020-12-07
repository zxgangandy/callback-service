package io.github.zxgangandy.callback.biz.converter;



import io.github.zxgangandy.callback.biz.bo.AddTaskReqBO;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

import static io.github.zxgangandy.callback.biz.constant.CallSuccessStatus.PREPARED;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Mapper(componentModel = "spring")
public interface TaskAddReqConverter extends BasicObjectMapper<AddTaskReqBO, CallbackTask> {

    default CallbackTask to(AddTaskReqBO source) {
        return new CallbackTask()
                .setCallCount(0)
                .setCallSuccess(PREPARED.getStatus())
                .setCallResult(EMPTY)
                .setCallExpect(source.getCallExpect())
                .setBizType(source.getBizType())
                .setReqMethod(source.getReqMethod())
                .setReqParam(source.getReqParam())
                .setSourceApp(source.getSourceApp())
                .setSourceIp(source.getSourceIp())
                .setTargetUrl(source.getTargetUrl())
                .setTargetApp(source.getTargetApp());
    }

}
