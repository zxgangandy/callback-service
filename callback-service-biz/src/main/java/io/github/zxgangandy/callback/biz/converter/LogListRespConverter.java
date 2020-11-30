package io.github.zxgangandy.callback.biz.converter;



import io.github.zxgangandy.callback.biz.bo.LogListRespBO;
import io.github.zxgangandy.callback.biz.bo.TaskListRespBO;
import io.github.zxgangandy.callback.biz.entity.CallbackLog;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import io.jingwei.base.utils.model.BasicObjectMapper;
import org.mapstruct.Mapper;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LogListRespConverter extends BasicObjectMapper<CallbackLog, LogListRespBO> {

    default List<LogListRespBO> to(Collection<CallbackLog> source) {
        if ( source == null ) {
            return null;
        }

        List<LogListRespBO> list = new ArrayList<>( source.size() );
        for ( CallbackLog callbackTask : source ) {
            LogListRespBO respBO = to(callbackTask);
            respBO.setTaskId(String.valueOf(callbackTask.getTaskId()));
            respBO.setCreateTime(callbackTask.getCtime().toEpochSecond(ZoneOffset.UTC));
            list.add(respBO);
        }

        return list;
    }
}
