package io.github.zxgangandy.callback.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskRegReq {

    /**
     * 回调源app名称
     */
    @NotBlank
    private String sourceApp;

    /**
     * 回调目标app名称
     */
    @NotBlank
    private String targetApp;

    /**
     * 业务类型
     */
    @NotBlank
    private String bizType;

}
