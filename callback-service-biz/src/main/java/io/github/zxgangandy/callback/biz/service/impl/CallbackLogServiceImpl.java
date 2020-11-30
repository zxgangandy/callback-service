package io.github.zxgangandy.callback.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zxgangandy.callback.biz.bo.LogListReqBO;
import io.github.zxgangandy.callback.biz.bo.LogListRespBO;
import io.github.zxgangandy.callback.biz.converter.LogListRespConverter;
import io.github.zxgangandy.callback.biz.entity.CallbackLog;
import io.github.zxgangandy.callback.biz.mapper.CallbackLogMapper;
import io.github.zxgangandy.callback.biz.service.ICallbackLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.jingwei.base.utils.time.DateUtils.getSecondToLocalDateTime;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Andy
 * @since 2020-11-25
 */
@Service
@Slf4j
public class CallbackLogServiceImpl extends ServiceImpl<CallbackLogMapper, CallbackLog> implements ICallbackLogService {

    @Autowired
    private LogListRespConverter logListRespConverter;
    @Override
    public Page<LogListRespBO> getLogList(LogListReqBO req) {
        Page<CallbackLog> page = new Page<>(req.getPageIndex(), req.getPageSize());

        //构造查询的条件参数
        QueryWrapper<CallbackLog> wrapper = getLogListWrapper(req);
        Page<CallbackLog> queryResult     =  page(page, wrapper);
        Page<LogListRespBO> pageResult    = new Page<>();
        pageResult.setCurrent(queryResult.getCurrent());
        pageResult.setSize(queryResult.getSize());
        pageResult.setTotal(queryResult.getTotal());
        pageResult.setRecords(logListRespConverter.to(queryResult.getRecords()));

        return pageResult;
    }


    /**
     * @Description: 构造查询的条件参数
     * @date 2020-11-27
     * @Param req:
     * @return: QueryWrapper<io.github.zxgangandy.callback.biz.entity.CallbackTask>
     */
    private QueryWrapper<CallbackLog> getLogListWrapper(LogListReqBO req) {
        QueryWrapper<CallbackLog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");

        if (StringUtils.isNotEmpty(req.getTaskId())) {
            wrapper.eq("task_id", Long.parseLong(req.getTaskId()));
        } else if(StringUtils.isNotEmpty(req.getReqParam())) {
            wrapper.like("req_param", req.getReqParam());
        } else if (StringUtils.isNotEmpty(req.getBizType())) {
            wrapper.eq("biz_type", req.getBizType());
        } else if (StringUtils.isNotEmpty(req.getSourceApp())) {
            wrapper.eq("source_app", req.getSourceApp());
        } else if (StringUtils.isNotEmpty(req.getSourceIp())) {
            wrapper.eq("source_ip", req.getSourceIp());
        } else if (StringUtils.isNotEmpty(req.getTargetApp())) {
            wrapper.eq("target_app", req.getTargetApp());
        } else if (StringUtils.isNotEmpty(req.getTargetUrl())) {
            wrapper.eq("target_url", req.getTargetUrl());
        } else if (StringUtils.isNotEmpty(req.getReqMethod())) {
            wrapper.eq("req_method", req.getReqMethod());
        }  else if(StringUtils.isNotEmpty(req.getCallResult())) {
            wrapper.like("call_result", req.getCallResult());
        } else if (req.getStartTime() != null) {
            wrapper.ge("ctime", getSecondToLocalDateTime(req.getStartTime()));
        } else if (req.getEndTime() != null) {
            wrapper.le("ctime", getSecondToLocalDateTime(req.getEndTime()));
        } else {
            log.info("In getLogListWrapper else, do nothing");
        }
        return wrapper;
    }
}
