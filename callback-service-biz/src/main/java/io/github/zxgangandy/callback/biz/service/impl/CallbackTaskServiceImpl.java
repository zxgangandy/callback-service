package io.github.zxgangandy.callback.biz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zxgangandy.callback.biz.bo.*;
import io.github.zxgangandy.callback.biz.converter.TaskAddReqConverter;
import io.github.zxgangandy.callback.biz.converter.TaskListRespBOConverter;
import io.github.zxgangandy.callback.biz.converter.Wrapper2LogConverter;
import io.github.zxgangandy.callback.biz.entity.CallbackLog;
import io.github.zxgangandy.callback.biz.service.ICallbackLogService;
import io.github.zxgangandy.callback.biz.service.IMqHandlerService;
import io.jingwei.base.idgen.UidGenerator;
import io.andy.rocketmq.wrapper.core.producer.RMProducer;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import io.github.zxgangandy.callback.biz.mapper.CallbackTaskMapper;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jingwei.base.utils.exception.BizErr;
import io.jingwei.base.utils.net.HttpClientUtil;
import io.jingwei.base.utils.tx.TxTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static io.github.zxgangandy.callback.biz.constant.CallSuccessStatus.*;
import static io.github.zxgangandy.callback.biz.exception.CallbackErrCode.*;
import static io.jingwei.base.utils.time.DateUtils.getSecondToLocalDateTime;
import static org.apache.commons.lang3.StringUtils.EMPTY;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Andy
 * @since 2020-11-19
 */
@Service
@Slf4j
public class CallbackTaskServiceImpl extends ServiceImpl<CallbackTaskMapper, CallbackTask> implements ICallbackTaskService {

    @Autowired
    private RMProducer              defaultProducer;
    @Autowired
    private UidGenerator            defaultUidGenerator;

    @Autowired
    private ICallbackLogService     callbackLogService;
    @Autowired
    private IMqHandlerService       mqHandlerService;
    @Autowired
    private TxTemplateService       txTemplateService;

    @Autowired
    private TaskListRespBOConverter taskListRespBOConverter;
    @Autowired
    private Wrapper2LogConverter    wrapper2LogConverter;
    @Autowired
    private TaskAddReqConverter     taskAddReqConverter;

    @Override
    public Optional<CallbackTask> getByTaskId(long taskId) {
        return lambdaQuery().eq(CallbackTask::getTaskId, taskId).oneOpt();
    }

    @Override
    public AddTaskRespBO addTask(AddTaskReqBO reqBO) {
        final AddTaskReqWrapperBO wrapper = createReqWrapper(reqBO);
        final String messageTopic = createMqTopic(reqBO);

        if (!mqHandlerService.exists(messageTopic)) {
            throw new BizErr(MSG_TOPIC_NOT_REG);
        }

        try {
            TransactionSendResult result = (TransactionSendResult) defaultProducer
                    .sendTransactional(messageTopic, wrapper, wrapper);
            if (result == null
                    || result.getLocalTransactionState().equals(LocalTransactionState.ROLLBACK_MESSAGE)
                    || result.getLocalTransactionState().equals(LocalTransactionState.UNKNOW)) {
                throw new BizErr(SEND_MSG_FAILED);
            }
        } catch (MQClientException e) {
            log.error("Mq send message error={}", e);
            throw new BizErr(SEND_MSG_FAILED);
        }

        return AddTaskRespBO.builder().taskId(wrapper.getTaskId()).build();
    }

    @Override
    public boolean execTask(AddTaskReqWrapperBO wrapper) throws IOException {
        final AddTaskReqBO req = wrapper.getReqBO();
        final String targetUrl = req.getTargetUrl();
        final String reqParam  = req.getReqParam();

        String resp;
        if (StringUtils.equalsIgnoreCase(req.getReqMethod(), "POST")) {
            resp = HttpClientUtil.getInstance().postSync(targetUrl, reqParam);
        } else {
            resp = HttpClientUtil.getInstance().getSync(targetUrl, null);
        }

        fillReqWrapper(wrapper, resp, req);
        execTaskComplete(wrapper);

        return Objects.equals(wrapper.getCallSuccess(), SUCCESS.getStatus());
    }

    @Override
    public boolean retryTask(long taskId) {
        Optional<CallbackTask> opt = getByTaskId(taskId);

        if (!opt.isPresent()) {
            throw new BizErr(TASK_NOT_FOUND);
        } else if(checkRetryNecessary(opt.get())) {
            return true; //已经成功回调到终态的任务不能再触发，直接返回
        }

        AddTaskReqWrapperBO wrapper = createRetryReqWrapper(opt, taskId);
        try {
            execTask(wrapper);
        } catch (IOException e) {
            log.error("retryTask failed, e={}", e);
            throw new BizErr(CALL_BACK_FAILED);
        }

        return true;
    }

    @Override
    public Page<TaskListRespBO> getTaskList(TaskListReqBO req) {
        Page<CallbackTask> page = new Page<>(req.getPageIndex(), req.getPageSize());

        //构造查询的条件参数
        QueryWrapper<CallbackTask> wrapper = getTaskListWrapper(req);
        Page<CallbackTask> queryResult =  page(page, wrapper);

        Page<TaskListRespBO> pageResult = new Page<>();
        pageResult.setCurrent(queryResult.getCurrent());
        pageResult.setSize(queryResult.getSize());
        pageResult.setPages(queryResult.getPages());
        pageResult.setRecords(taskListRespBOConverter.to(queryResult.getRecords()));

        return pageResult;
    }

    /**
     * @Description: 构建请求的wrapper对象
     * @date 2020-11-27
     * @Param reqBO:
     * @return: io.github.zxgangandy.callback.biz.bo.AddTaskReqWrapperBO
     */
    private AddTaskReqWrapperBO createReqWrapper(AddTaskReqBO reqBO) {
        String taskId = String.valueOf(defaultUidGenerator.getUID());
        AddTaskReqWrapperBO wrapper = new AddTaskReqWrapperBO().setReqBO(reqBO).setTaskId(taskId);

        return wrapper;
    }

    /**
     * @Description: 构造Mq的topic
     * @date 2020-11-27
     * @Param reqBO:
     * @return: java.lang.String
     */
    private static String createMqTopic(AddTaskReqBO reqBO) {
        return reqBO.getSourceApp() + "-" + reqBO.getTargetApp() + "-" + reqBO.getBizType();
    }

    /**
     * @Description: 构建retry task请求的wrapper对象
     * @date 2020-11-30
     * @Param opt:
     * @Param taskId:
     * @return: io.github.zxgangandy.callback.biz.bo.AddTaskReqWrapperBO
     */
    private AddTaskReqWrapperBO createRetryReqWrapper(Optional<CallbackTask> opt, long taskId) {
        AddTaskReqBO req = taskAddReqConverter.from(opt.get());
        AddTaskReqWrapperBO wrapper = new AddTaskReqWrapperBO();
        wrapper.setReqBO(req).setTaskId(String.valueOf(taskId));
        return wrapper;
    }

    /**
     * @Description: 构造查询的条件参数
     * @date 2020-11-27
     * @Param req:
     * @return: QueryWrapper<io.github.zxgangandy.callback.biz.entity.CallbackTask>
     */
    private QueryWrapper<CallbackTask> getTaskListWrapper(TaskListReqBO req) {
        QueryWrapper<CallbackTask> wrapper = new QueryWrapper<>();
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
        } else if (StringUtils.isNotEmpty(req.getCallSuccess())) {
            wrapper.eq("call_success", req.getCallSuccess());
        } else if(StringUtils.isNotEmpty(req.getCallResult())) {
            wrapper.like("call_result", req.getCallResult());
        } else if(StringUtils.isNotEmpty(req.getCallExpect())) {
            wrapper.like("call_expect", req.getCallExpect());
        } else if (req.getStartTime() != null) {
            wrapper.ge("ctime", getSecondToLocalDateTime(req.getStartTime()));
        } else if (req.getEndTime() != null) {
            wrapper.le("ctime", getSecondToLocalDateTime(req.getEndTime()));
        } else {
            log.info("In getTaskListWrapper else, do nothing");
        }
        return wrapper;
    }

    private void execTaskComplete(AddTaskReqWrapperBO wrapper) {
        txTemplateService.doInTransaction(() -> {
            boolean result = updateCallResult(wrapper);
            if (result) {
                saveLog(wrapper);
            } else {
                log.error("execTaskComplete=>updateCallResult failed, wrapper={}", wrapper);
            }
        });
    }

    /**
     * @Description: 更新调用结果的状态到终态
     * @date 2020-11-27
     * @Param callSuccess:
     * @Param resp:
     * @Param taskId:
     * @return: void
     */
    private boolean updateCallResult(AddTaskReqWrapperBO wrapper) {
        String[] successList = new String[]{SUCCESS.getStatus()};
        boolean result = lambdaUpdate()
                .set(CallbackTask::getCallSuccess, wrapper.getCallSuccess())
                .set(CallbackTask::getCallResult, wrapper.getCallResult())
                .setSql("call_count = call_count + 1")
                .eq(CallbackTask::getTaskId, Long.parseLong(wrapper.getTaskId()))
                .notIn(CallbackTask::getCallSuccess, Arrays.asList(successList))
                .update();

        return result;
    }

    /**
     * @Description: 保存回调的日志
     * @date 2020-11-27
     * @Param wrapper:
     * @Param callResult:
     * @return: void
     */
    private void saveLog(AddTaskReqWrapperBO wrapper) {
        CallbackLog callbackLog = wrapper2LogConverter.to(wrapper);
        callbackLogService.save(callbackLog);
    }

    private void fillReqWrapper(AddTaskReqWrapperBO wrapper, String resp, AddTaskReqBO req) {
        if (invalidResponse(resp, req.getCallExpect())) {
            log.warn("resp is empty, or length than expect");

            wrapper.setCallResult(EMPTY);
            wrapper.setCallSuccess(FAILED.getStatus());
        } else if (req.getCallExpect().contains(resp)) {
            wrapper.setCallResult(resp);
            wrapper.setCallSuccess(SUCCESS.getStatus());
        } else {
            log.warn("resp not in expect results");

            wrapper.setCallResult(resp);
            wrapper.setCallSuccess(FAILED.getStatus());
        }
    }

    private boolean checkRetryNecessary(CallbackTask callbackTask) {
        return Objects.equals(callbackTask.getCallSuccess(), SUCCESS.getStatus());
    }

    private boolean invalidResponse(String resp, String callExpect) {
        return resp == null || resp.length() == 0 || resp.length() > callExpect.length();
    }

}
