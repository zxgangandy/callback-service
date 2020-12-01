package io.github.zxgangandy.callback.biz.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TaskTotalInfoRespBO {
    private long total;

    private long success;

    private long failed;
}
