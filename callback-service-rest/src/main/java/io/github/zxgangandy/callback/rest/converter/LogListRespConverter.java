package io.github.zxgangandy.callback.rest.converter;



import io.github.zxgangandy.callback.biz.bo.LogListRespBO;
import io.github.zxgangandy.callback.model.LogListResp;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogListRespConverter extends BasicObjectMapper<LogListRespBO, LogListResp> {

}
