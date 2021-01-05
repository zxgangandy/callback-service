package io.github.zxgangandy.callback.biz.mq;

import io.github.zxgangandy.callback.biz.entity.CallbackReg;
import io.github.zxgangandy.callback.biz.service.ICallbackRegService;
import io.github.zxgangandy.callback.biz.service.IMqHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
public class ConsumerLoader implements CommandLineRunner {

    @Autowired
    private ICallbackRegService callbackRegService;
    @Autowired
    private IMqHandlerService   mqHandlerService;

    @Override
    public void run(String... args) throws Exception {
        List<CallbackReg> list = callbackRegService.list();

        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(item->{
                try {
                    mqHandlerService.subscribe(getConsumeTopic(item));
                } catch (Exception ex) {
                    log.error("Load task consumer failed: error={}", ex);
                }
            });
        }
    }

    private String getConsumeTopic(CallbackReg reg) {
        return reg.getSourceApp() + "-" + reg.getTargetApp() + "-" + reg.getBizType();
    }
}
