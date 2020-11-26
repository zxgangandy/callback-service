package io.github.zxgangandy.callback.rest.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zxgangandy.callback.biz.bo.TaskListReqBO;
import io.github.zxgangandy.callback.biz.bo.TaskListRespBO;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import io.github.zxgangandy.callback.model.TaskListReq;
import io.github.zxgangandy.callback.model.TaskListResp;
import io.github.zxgangandy.callback.rest.converter.TaskListReqConverter;
import io.github.zxgangandy.callback.rest.converter.TaskListRespConverter;
import io.jingwei.base.utils.model.P;
import io.jingwei.base.utils.model.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
    private final ICallbackTaskService  callbackTaskService;
    private final TaskListReqConverter  taskListReqConverter;
    private final TaskListRespConverter taskListRespConverter;

    @PostMapping(V_1 +"/list")
    public R<P<TaskListResp>> list(@RequestBody @Valid TaskListReq req) {
        TaskListReqBO reqBO       = taskListReqConverter.to(req);
        Page<TaskListRespBO> page = callbackTaskService.getList(reqBO);
        List<TaskListResp> list   = taskListRespConverter.to(page.getRecords());

        P result = new P<>();
        result.setPageIndex(req.getPageIndex());
        result.setPageSize(req.getPageSize());
        result.setPages(page.getPages());
        result.setRows(list);
        return R.ok(result);
    }


}
