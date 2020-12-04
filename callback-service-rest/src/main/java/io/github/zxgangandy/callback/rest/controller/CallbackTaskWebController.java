package io.github.zxgangandy.callback.rest.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zxgangandy.callback.biz.bo.*;
import io.github.zxgangandy.callback.biz.service.ICallbackLogService;
import io.github.zxgangandy.callback.biz.service.ICallbackRegService;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import io.github.zxgangandy.callback.model.*;
import io.github.zxgangandy.callback.rest.converter.*;
import io.jingwei.base.utils.model.P;
import io.jingwei.base.utils.model.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static io.jingwei.base.utils.model.ModelConverter.page;
import static io.jingwei.base.utils.constant.ApiConstant.V_1;

/**
 * <p>
 * callback
 * </p>
 *
 * @author Andy
 * @since 2020-11-19
 */
@RestController
@RequestMapping("/web/callback")
@AllArgsConstructor
public class CallbackTaskWebController {
    private final ICallbackTaskService       callbackTaskService;
    private final ICallbackLogService        callbackLogService;
    private final ICallbackRegService        callbackRegService;
    private final TaskListReqConverter       taskListReqConverter;
    private final TaskListRespConverter      taskListRespConverter;
    private final LogListReqConverter        logListReqConverter;
    private final LogListRespConverter       logListRespConverter;
    private final TaskRegReqConverter        taskRegReqConverter;
    private final TaskTotalInfoRespConverter taskTotalInfoRespConverter;

    @PostMapping(V_1 +"/task_list")
    public R<P<TaskListResp>> getTaskList(@RequestBody @Valid TaskListReq req) {
        TaskListReqBO reqBO       = taskListReqConverter.to(req);
        Page<TaskListRespBO> page = callbackTaskService.getTaskList(reqBO);
        List<TaskListResp> list   = taskListRespConverter.to(page.getRecords());
        P<TaskListResp> result    = page(req.getPageIndex(), req.getPageSize(), page.getPages(), list);
        return R.ok(result);
    }

    @PostMapping(V_1 +"/log_list")
    public R<P<LogListResp>> getLogList(@RequestBody @Valid LogListReq req) {
        LogListReqBO reqBO       = logListReqConverter.to(req);
        Page<LogListRespBO> page = callbackLogService.getLogList(reqBO);
        List<LogListResp> list   = logListRespConverter.to(page.getRecords());
        P<LogListResp> result    = page(req.getPageIndex(), req.getPageSize(), page.getPages(), list);

        return R.ok(result);
    }

    @PostMapping(V_1 +"/retry_task")
    public R<RetryTaskResp> retryTask(@RequestBody @Valid RetryTaskReq req) {
        final long taskId      = Long.parseLong(req.getTaskId());
        boolean retryResult    = callbackTaskService.retryTask(taskId);
        RetryTaskResp taskResp = new RetryTaskResp().setResult(retryResult);
        return R.ok(taskResp);
    }

    @GetMapping(V_1 +"/total_info")
    public R<TaskTotalInfoResp> totalInfo() {
        TaskTotalInfoRespBO respBO = callbackLogService.getTotalTaskInfo();
        TaskTotalInfoResp resp     = taskTotalInfoRespConverter.to(respBO);
        return R.ok(resp);
    }

    @PostMapping(V_1 +"/task_reg")
    public R<TaskRegResp> taskReg(@RequestBody @Valid TaskRegReq req) {
        TaskRegReqBO taskRegReqBO = taskRegReqConverter.to(req);
        boolean regResult         = callbackRegService.register(taskRegReqBO);
        TaskRegResp taskRegResp   = new TaskRegResp().setResult(regResult);
        return R.ok(taskRegResp);
    }

}
