package io.github.zxgangandy.callback.biz.exception;


import io.jingwei.base.utils.exception.IBizErrCode;

public enum CallbackErrCode implements IBizErrCode {
    TASK_NOT_FOUND("10000", "task-not-found"),
    SEND_MSG_FAILED("10001", "send-message-failed"),
    CALL_BACK_FAILED("10002", "call-third-party-failed"),
    MSG_TOPIC_NOT_REG("10003", "message-topic-not-register")
    ;

    /**
     * 枚举编码
     */
    private String code;

    /**
     * 描述说明
     */
    private String desc;

    CallbackErrCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String getMsg() {
        return getClass().getName() + '.' + name();
    }

}
