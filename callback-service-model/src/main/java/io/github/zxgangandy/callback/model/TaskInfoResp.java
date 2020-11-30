package io.github.zxgangandy.callback.model;

import lombok.Data;

@Data
public class TaskInfoResp {
    private long total;

    private long success;

    private long failed;
}
