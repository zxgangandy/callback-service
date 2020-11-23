package io.github.zxgangandy.callback.biz.bo;

import lombok.Data;

@Data
public class AddTaskReqBO {
    /**
     * 请求方法
     */
    private String reqMethod;

    /**
     * 请求参数
     */
    private String reqParam;

    /**
     * 回调源app名称
     */
    private String sourceApp;

    /**
     * 回调目标app名称
     */
    private String targetApp;

    /**
     * 回调源app的IP
     */
    private String sourceIp;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 回调期望值
     */
    private String callExpect;
}
