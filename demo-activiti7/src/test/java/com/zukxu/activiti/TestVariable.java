package com.zukxu.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xupu
 * @date 2021/11/21 23:39:30
 */
@Slf4j
@SpringBootTest
public class TestVariable {

    private final String key = "evection-variable";

    /**
     * 查询任务
     */
    @Test
    void variableQuery() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                                         .processDefinitionKey(key)
                                         //.taskAssignee("杨总经理")
                                         .list();
        if(taskList != null && taskList.size() > 0) {
            for(Task task : taskList) {
                log.info("任务id:{}", task.getId());
                log.info("任务名称:{}", task.getName());
            }
        }
    }
    /**
     * 处理第一个流程实例 请假天数<3
     */
    @Test
    void testTaskCompleteByUser1() {

        String assignee0 = "李四";
        String assignee1 = "王经理";
        String assignee2 = "张财务";
        String assignee3 = "杨总经理";

        //    获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取TaskService
        TaskService taskService = processEngine.getTaskService();
        //    根据流程定义key和用户名查询任务,返回一个任务对象
        Task task = taskService.createTaskQuery()
                               .processDefinitionKey(key)
                               .taskAssignee(assignee3)
                               .singleResult();
        if(task == null) {
            log.info("无待执行任务");
            return;
        }
        //    根据任务id完成任务
        taskService.complete(task.getId());
        log.info("编号{}任务完成", task.getId());
    }

    /**
     *  处理第二个流程实例 请假天数>=3
     */
    @Test
    void testTaskCompleteByUser2() {

        String assignee0 = "李四1";
        String assignee1 = "王经理1";
        String assignee2 = "张财务1";
        String assignee3 = "杨总经理1";

        //    获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取TaskService
        TaskService taskService = processEngine.getTaskService();
        //    根据流程定义key和用户名查询任务,返回一个任务对象
        Task task = taskService.createTaskQuery()
                               .processDefinitionKey(key)
                               .taskAssignee(assignee3)
                               .singleResult();
        if(task == null) {
            log.info("无待执行任务");
            return;
        }
        //    根据任务id完成任务
        taskService.complete(task.getId());
        log.info("编号{}任务完成", task.getId());
    }

}
