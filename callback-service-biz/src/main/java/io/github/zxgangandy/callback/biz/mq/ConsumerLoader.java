package io.github.zxgangandy.callback.biz.mq;

import io.github.zxgangandy.callback.biz.entity.CallbackReg;
import io.github.zxgangandy.callback.biz.service.ICallbackRegService;
import io.github.zxgangandy.callback.biz.service.IMqHandlerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ConsumerLoader  {

    @Autowired
    private ICallbackRegService callbackRegService;
    @Autowired
    private IMqHandlerService   mqHandlerService;

    @PostConstruct
    public void init() {
        List<CallbackReg> list = callbackRegService.list();

        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(item->{
                mqHandlerService.subscribe(getConsumeTopic(item));
            });
        }
    }

    private String getConsumeTopic(CallbackReg reg) {
        return reg.getSourceApp() + "-" + reg.getTargetApp() + "-" + reg.getBizType();
    }

}
