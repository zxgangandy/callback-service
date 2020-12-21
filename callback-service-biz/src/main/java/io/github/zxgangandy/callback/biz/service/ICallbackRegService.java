package io.github.zxgangandy.callback.biz.service;

import io.github.zxgangandy.callback.biz.bo.TaskRegReqBO;
import io.github.zxgangandy.callback.biz.entity.CallbackReg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Andy
 * @since 2020-12-04
 */
public interface ICallbackRegService extends IService<CallbackReg> {

    boolean register(TaskRegReqBO taskRegReqBO);

}
