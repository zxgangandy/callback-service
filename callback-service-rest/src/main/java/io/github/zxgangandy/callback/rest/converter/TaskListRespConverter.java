package io.github.zxgangandy.callback.rest.converter;



import io.github.zxgangandy.callback.biz.bo.TaskListRespBO;
import io.github.zxgangandy.callback.model.TaskListResp;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskListRespConverter extends BasicObjectMapper<TaskListRespBO, TaskListResp> {

}
