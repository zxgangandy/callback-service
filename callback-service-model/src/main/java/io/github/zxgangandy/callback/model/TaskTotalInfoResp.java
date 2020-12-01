package io.github.zxgangandy.callback.model;

import lombok.Data;

@Data
public class TaskTotalInfoResp {
    private long total;

    private long success;

    private long failed;
}
