package io.github.zxgangandy.callback.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddTaskReq {
    /**
     * 请求方法
     */

    @NotBlank
    private String reqMethod;

    /**
     * 请求参数
     */
    @NotBlank
    private String reqParam;

    /**
     * 回调源app名称
     */
    @NotBlank
    private String sourceApp;

    /**
     * 回调目标app名称
     */
    private String targetApp;

    /**
     * 回调目标url地址
     */
    @NotBlank
    private String targetUrl;

    /**
     * 业务类型
     */
    @NotBlank
    private String bizType;

    /**
     * 回调期望值
     */
    @NotBlank
    private String callExpect;

}
