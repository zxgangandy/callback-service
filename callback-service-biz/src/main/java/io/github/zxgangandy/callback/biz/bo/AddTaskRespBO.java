package io.github.zxgangandy.callback.biz.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddTaskRespBO {
    /**
     * 任务id
     */
    private String taskId;

}
