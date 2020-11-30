package io.github.zxgangandy.callback.biz.exception;


import io.jingwei.base.utils.exception.IBizErrCode;

public enum CallbackErrCode implements IBizErrCode {
    TASK_NOT_FOUND("10000", "task-not-found"),
    SEND_MSG_FAILED("10001", "send-message-failed"),
    CALL_BACK_FAILED("10002", "call-third-party-failed"),
    CREATE_OTC_ORDER_FAILED("12303", "createSimpleOrder-otc-order-failed"),
    ILLEGAL_MERCHANT_ID("12304", "illegal-merchant-id"),
    MERCHANT_DISABLED("12305", "merchant-disabled"),
    MERCHANT_NOT_INIT("12306", "merchant-not-init"),
    SECRET_KEY_EXPIRED("12307", "secret-key-expired"),
    ORDER_NOT_FOUND("12308", "order-not-found"),
    TRIGGER_PRICE_INVALID("12008", "trigger-price-invalid"),
    ORDER_VOLUME_INVALID("12009", "order-volume-invalid"),
    ORDER_TYPE_NOT_SUPPORT("12010", "order-type-not-support"),
    PLACE_ORDER_FAILED("12015", "place-order-failed")
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
