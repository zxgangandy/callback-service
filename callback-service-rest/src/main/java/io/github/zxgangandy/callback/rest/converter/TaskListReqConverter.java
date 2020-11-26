package io.github.zxgangandy.callback.rest.converter;


import io.github.zxgangandy.callback.biz.bo.TaskListReqBO;
import io.github.zxgangandy.callback.model.TaskListReq;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskListReqConverter extends BasicObjectMapper<TaskListReq, TaskListReqBO> {

}
