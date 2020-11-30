package io.github.zxgangandy.callback.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zxgangandy.callback.biz.bo.LogListReqBO;
import io.github.zxgangandy.callback.biz.bo.LogListRespBO;
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
}
