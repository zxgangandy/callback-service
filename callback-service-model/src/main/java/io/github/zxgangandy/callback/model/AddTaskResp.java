package io.github.zxgangandy.callback.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTaskResp {
    /**
     * 任务id
     */
    private String taskId;

}
