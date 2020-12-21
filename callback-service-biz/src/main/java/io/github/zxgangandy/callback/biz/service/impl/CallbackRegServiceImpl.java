package io.github.zxgangandy.callback.biz.service.impl;

import io.github.zxgangandy.callback.biz.bo.TaskRegReqBO;
import io.github.zxgangandy.callback.biz.entity.CallbackReg;
import io.github.zxgangandy.callback.biz.mapper.CallbackRegMapper;
import io.github.zxgangandy.callback.biz.service.ICallbackRegService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.zxgangandy.callback.biz.service.IMqHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Andy
 * @since 2020-12-04
 */
@Service
public class CallbackRegServiceImpl extends ServiceImpl<CallbackRegMapper, CallbackReg> implements ICallbackRegService {

    @Autowired
    private IMqHandlerService mqHandlerService;

    @Override
    public boolean register(TaskRegReqBO reqBO) {
        Optional<CallbackReg> opt = lambdaQuery()
                .eq(CallbackReg::getSourceApp, reqBO.getSourceApp())
                .eq(CallbackReg::getTargetApp, reqBO.getTargetApp())
                .eq(CallbackReg::getBizType, reqBO.getBizType())
                .oneOpt();

        if (opt.isPresent()) {
            return true;
        }

        CallbackReg callbackReg = new CallbackReg()
                .setSourceApp(reqBO.getSourceApp())
                .setTargetApp(reqBO.getTargetApp())
                .setBizType(reqBO.getBizType());

        boolean result;
        try{
            result = save(callbackReg);
            String topic = getConsumeTopic(reqBO);
            mqHandlerService.subscribe(topic);
        } catch (Exception ex) {
            log.error("register failed, ex={}", ex);
            result = false;
        }

        return result;
    }

    private String getConsumeTopic(TaskRegReqBO reqBO) {
        return reqBO.getSourceApp() + "-" + reqBO.getTargetApp() + "-" + reqBO.getBizType();
    }
}
