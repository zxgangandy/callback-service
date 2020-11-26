package io.github.zxgangandy.callback.biz.converter;



import io.github.zxgangandy.callback.biz.bo.TaskListRespBO;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskListRespConverter extends BasicObjectMapper<CallbackTask, TaskListRespBO> {

}
