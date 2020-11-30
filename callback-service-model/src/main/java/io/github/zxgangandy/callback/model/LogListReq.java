package io.github.zxgangandy.callback.model;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author Andy
 * @since 2020-11-24
 */
@Data
public class LogListReq {
    private String taskId;

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
     * 回调源app的IP
     */
    private String sourceIp;

    /**
     * 回调目标app名称
     */
    private String targetApp;

    /**
     * 回调目标url地址
     */
    private String targetUrl;

    /**
     * 回调的业务类型
     */
    private String bizType;


    /**
     * 回调是否成功(成功：SUCCESS， 失败：FAILED)
     */
    private String callSuccess;

    /**
     * 回调实际值
     */
    private String callResult;

    /**
     * 开始时间
     */
    private Long  startTime;

    /**
     * 结束时间
     */
    private Long  endTime;


    private Integer pageIndex = 0;

    private Integer pageSize = 10;


}
