package io.github.zxgangandy.callback.biz.service;

public interface IMqHandlerService {
    void subscribe(String topic);

    boolean exists(String topic);
}
