package io.github.zxgangandy.callback.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zxgangandy.callback.biz.bo.LogListReqBO;
import io.github.zxgangandy.callback.biz.bo.LogListRespBO;
import io.github.zxgangandy.callback.biz.bo.TaskTotalInfoRespBO;
import io.github.zxgangandy.callback.biz.entity.CallbackLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Andy
 * @since 2020-11-25
 */
public interface ICallbackLogService extends IService<CallbackLog> {
    /**
     * @Description: 根据条件获取task log列表
     * @date 2020-11-30
     * @Param req:
     * @return: Page<io.github.zxgangandy.callback.biz.bo.LogListRespBO>
     */
    Page<LogListRespBO> getLogList(LogListReqBO req);

    /**
     * @Description: 获取总的回调次数
     * @date 2020-12-02
     * @Param resp:
     * @return: long
     */
    long getTotal(TaskTotalInfoRespBO resp);

    /**
     * @Description: 获取总成功的回调次数
     * @date 2020-12-02
     * @Param resp:
     * @return: long
     */
    long getSuccessTotal(TaskTotalInfoRespBO resp);

    /**
     * @Description: 获取总失败的回调次数
     * @date 2020-12-02
     * @Param total:
     * @Param success:
     * @return: long
     */
    long getFailedTotal(long total, long success);

    /**
     * @Description: 获取总的回调信息（包括总回调次数，总成功、总失败数）
     * @date 2020-12-02
     *
     * @return: io.github.zxgangandy.callback.biz.bo.TaskTotalInfoRespBO
     */
    TaskTotalInfoRespBO getTotalTaskInfo();
}
