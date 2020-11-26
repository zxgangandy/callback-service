package io.github.zxgangandy.callback.biz.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Andy
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TaskListRespBO {

    /**
     * 回调任务业务id
     */
    private Long taskId;

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
     * 回调次数
     */
    private Integer callCount;

    /**
     * 回调是否成功(成功：SUCCESS， 失败：FAILED)
     */
    private String callSuccess;

    /**
     * 回调期望值
     */
    private String callExpect;

    /**
     * 回调实际值
     */
    private String callResult;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;



}
