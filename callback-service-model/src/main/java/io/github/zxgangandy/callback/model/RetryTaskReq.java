package io.github.zxgangandy.callback.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RetryTaskReq {
    /**
     * task id
     */

    @NotBlank
    private String taskId;



}
