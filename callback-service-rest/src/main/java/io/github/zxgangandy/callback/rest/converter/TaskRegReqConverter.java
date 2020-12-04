package io.github.zxgangandy.callback.rest.converter;



import io.github.zxgangandy.callback.biz.bo.TaskRegReqBO;
import io.github.zxgangandy.callback.model.TaskRegReq;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskRegReqConverter extends BasicObjectMapper<TaskRegReq, TaskRegReqBO> {

}
