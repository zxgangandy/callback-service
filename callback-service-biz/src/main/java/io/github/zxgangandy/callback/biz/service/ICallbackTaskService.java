package io.github.zxgangandy.callback.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.zxgangandy.callback.biz.bo.*;
import io.github.zxgangandy.callback.biz.entity.CallbackTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.util.Optional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Andy
 * @since 2020-11-19
 */
public interface ICallbackTaskService extends IService<CallbackTask> {

    /**
     * @Description: 根据taskId查询task
     * @date 2020-11-27
     * @Param taskId:
     * @return: io.github.zxgangandy.callback.biz.entity.CallbackTask
     */
    Optional<CallbackTask> getByTaskId(long taskId);

    /**
     * @Description: 增加task
     * @date 2020-11-27
     * @Param reqBO:
     * @return: io.github.zxgangandy.callback.biz.bo.AddTaskRespBO
     */
    AddTaskRespBO addTask(AddTaskReqBO reqBO);

    /**
     * @Description: 执行task
     * @date 2020-11-27
     * @Param wrapper:
     * @return: void
     */
    void execTask(AddTaskReqWrapperBO wrapper) throws IOException;

    /**
     * @Description: 重新执行task
     * @date 2020-11-27
     * @Param taskId:
     * @return: void
     */
    boolean retryTask(long taskId);

    /**
     * @Description: 执行任务失败后的操作
     * @date 2020-11-27
     * @Param wrapper:
     * @Param callResult:
     * @return: void
     */
    void execFailedResult(AddTaskReqWrapperBO wrapper, String callResult);

    /**
     * @Description: 执行任务成功后的操作
     * @date 2020-11-27
     * @Param wrapper:
     * @Param callResult:
     * @Param callSuccess:
     * @return: void
     */
    void execSuccessResult(AddTaskReqWrapperBO wrapper, String callResult, String callSuccess);

    /**
     * @Description: 根据条件获取task列表
     * @date 2020-11-27
     * @Param req:
     * @return: Page<io.github.zxgangandy.callback.biz.bo.TaskListRespBO>
     */
    Page<TaskListRespBO> getTaskList(TaskListReqBO req);



}
