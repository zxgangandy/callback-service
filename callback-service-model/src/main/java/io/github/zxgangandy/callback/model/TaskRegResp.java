package io.github.zxgangandy.callback.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class TaskRegResp {
    /**
     * 注册结果
     */

    @NotBlank
    private boolean result;



}
