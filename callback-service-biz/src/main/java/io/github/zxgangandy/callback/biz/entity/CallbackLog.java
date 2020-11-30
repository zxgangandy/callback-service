package io.github.zxgangandy.callback.biz.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Andy
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CallbackLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Long id;

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
     * 回调实际值
     */
    private String callResult;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime utime;


}
