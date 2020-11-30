package io.github.zxgangandy.callback.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class RetryTaskResp {
    /**
     * 重试结果
     */

    @NotBlank
    private boolean result;



}
