package io.github.zxgangandy.callback.rest.converter;



import io.github.zxgangandy.callback.biz.bo.TaskTotalInfoRespBO;
import io.github.zxgangandy.callback.model.TaskTotalInfoResp;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskTotalInfoRespConverter extends BasicObjectMapper<TaskTotalInfoRespBO, TaskTotalInfoResp> {

}
