package io.github.zxgangandy.callback.biz.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CallSuccessStatus {
    PREPARED("PREPARED"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    private String status;
}
