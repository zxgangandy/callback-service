package io.github.zxgangandy.callback.biz.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTaskReqWrapperBO {
    private AddTaskReqBO reqBO;
    private String taskId;
}
