package io.github.zxgangandy.callback.rest.controller;


import io.github.zxgangandy.callback.biz.bo.AddTaskReqBO;
import io.github.zxgangandy.callback.biz.bo.AddTaskRespBO;
import io.github.zxgangandy.callback.biz.service.ICallbackTaskService;
import io.github.zxgangandy.callback.model.AddTaskReq;
import io.github.zxgangandy.callback.model.AddTaskResp;
import io.github.zxgangandy.callback.rest.converter.AddTaskReqConverter;
import io.github.zxgangandy.callback.rest.converter.AddTaskRespConverter;
import io.jingwei.base.utils.model.R;

import io.jingwei.base.utils.net.IpUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
@RequestMapping("/callback")
@AllArgsConstructor
public class CallbackTaskApiController {
    private final AddTaskReqConverter  addTaskReqConverter;
    private final AddTaskRespConverter addTaskRespConverter;
    private final ICallbackTaskService callbackTaskService;

    @PostMapping(V_1 +"/add_task")
    public R<AddTaskResp> createSimplePayOrder(@RequestBody @Valid AddTaskReq req) {
        AddTaskReqBO reqBO   = addTaskReqConverter.to(req);
        reqBO.setSourceIp(IpUtil.getIp());

        AddTaskRespBO respBO = callbackTaskService.addTask(reqBO);
        AddTaskResp resp     = addTaskRespConverter.from(respBO);

        return R.ok(resp);
    }


}
