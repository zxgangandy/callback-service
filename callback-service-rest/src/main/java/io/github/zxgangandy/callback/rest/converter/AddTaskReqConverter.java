package io.github.zxgangandy.callback.rest.converter;



import io.github.zxgangandy.callback.biz.bo.AddTaskReqBO;
import io.github.zxgangandy.callback.model.AddTaskReq;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddTaskReqConverter extends BasicObjectMapper<AddTaskReq, AddTaskReqBO> {

}
