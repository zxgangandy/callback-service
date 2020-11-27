package io.github.zxgangandy.callback.biz.bo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddTaskReqWrapperBO {
    private AddTaskReqBO reqBO;
    private String taskId;
}
