package io.github.zxgangandy.callback.biz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CallbackReg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 回调源app名称
     */
    private String sourceApp;

    /**
     * 回调目标app名称
     */
    private String targetApp;

    /**
     * 回调的业务类型
     */
    private String bizType;

    /**
     * 创建时间
     */
    private LocalDateTime ctime;

    /**
     * 更新时间
     */
    private LocalDateTime utime;


}
