package io.github.zxgangandy.callback.rest.converter;



import io.github.zxgangandy.callback.biz.bo.AddTaskRespBO;
import io.github.zxgangandy.callback.model.AddTaskResp;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddTaskRespConverter extends BasicObjectMapper<AddTaskResp, AddTaskRespBO> {

}
