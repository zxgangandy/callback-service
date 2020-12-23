package io.github.zxgangandy.callback.client.feign;

import io.github.zxgangandy.callback.model.AddTaskReq;
import io.github.zxgangandy.callback.model.AddTaskResp;
import io.jingwei.base.utils.model.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient("callback-service")
public interface CallbackClient {
    String baseUrl = "/api/callback/v1";

    @GetMapping({baseUrl + "/add_task"})
    R<AddTaskResp> addTask(AddTaskReq req);

}
