package io.github.zxgangandy.callback.rest.converter;


import io.github.zxgangandy.callback.biz.bo.LogListReqBO;
import io.github.zxgangandy.callback.model.LogListReq;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LogListReqConverter extends BasicObjectMapper<LogListReq, LogListReqBO> {

}
