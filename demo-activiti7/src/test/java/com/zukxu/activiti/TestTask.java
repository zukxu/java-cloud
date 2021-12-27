package com.zukxu.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xupu
 * @date 2021/11/20 18:09:41
 */
@Slf4j
@SpringBootTest
public class TestTask {

    private final String processDefinitionKey = "Leave-1";

    /**
     * 任务负责人 worker 在正式开发中就是查询的用户名
     */
    private final String assignee_worker = "worker";

    private final String assignee_manager = "manager";

    private final String assignee_finance = "financer";

    /**
     * 查询当前个人待执行任务
     */
    @Test
    void testFindTaskList() {
        //    创建taskService
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //    根据流程key【流程定义ID】 和任务负责人查询任务列表
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey);
        //指定任务负责人
        List<Task> workerList = taskQuery.taskAssignee(assignee_worker).list();
        List<Task> managerList = taskQuery.taskAssignee(assignee_manager).list();
        List<Task> financeList = taskQuery.taskAssignee(assignee_finance).list();
        //    输出信息
        log.info("=================worker===============");
        log.info("worker待执行任务:{}个", workerList.size());
        for(Task task : workerList) {
            log.info("流程实例id:{}", task.getProcessInstanceId());
            log.info("任务id:{}", task.getId());
            log.info("任务负责人:{}", task.getAssignee());
            log.info("任务名称:{}", task.getName());
        }
        log.info("=================manager===============");
        log.info("manager待执行任务:{}个", managerList.size());
        for(Task task : managerList) {
            log.info("流程实例id:{}", task.getProcessInstanceId());
            log.info("任务id:{}", task.getId());
            log.info("任务负责人:{}", task.getAssignee());
            log.info("任务名称:{}", task.getName());
        }
        log.info("=================finance===============");
        log.info("finance待执行任务:{}个", financeList.size());
        for(Task task : financeList) {
            log.info("流程实例id:{}", task.getProcessInstanceId());
            log.info("任务id:{}", task.getId());
            log.info("任务负责人:{}", task.getAssignee());
            log.info("任务名称:{}", task.getName());
        }
    }

    /**
     * 根据查询出来的任务id进行完成任务
     */
    @Test
    void testTaskCompleteByWorker() {
        //    获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取TaskService
        TaskService taskService = processEngine.getTaskService();
        //    根据流程定义key和用户名查询任务,返回一个任务对象
        Task task = taskService.createTaskQuery()
                               .processDefinitionKey(processDefinitionKey)
                               .taskAssignee(assignee_worker)
                               .singleResult();
        if(task == null) {
            log.info("worker无待执行任务");
            return;
        }
        //    根据任务id完成任务
        taskService.complete(task.getId());
        log.info("worker{}任务完成", task.getId());
    }

    @Test
    void testTaskCompleteByManager() {
        //    获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取TaskService
        TaskService taskService = processEngine.getTaskService();
        //    根据流程定义key和用户名查询任务,返回一个任务对象
        Task task = taskService.createTaskQuery()
                               .processDefinitionKey(processDefinitionKey)
                               .taskAssignee(assignee_manager)
                               .singleResult();
        if(task == null) {
            log.info("manager无待执行任务");
            return;
        }
        //    根据任务id完成任务
        taskService.complete(task.getId());
        log.info("manager{}任务完成", task.getId());
    }

    @Test
    void testTaskCompleteByFinance() {
        //    获取processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取TaskService
        TaskService taskService = processEngine.getTaskService();
        //    根据流程定义key和用户名查询任务,返回一个任务对象
        Task task = taskService.createTaskQuery()
                               .processDefinitionKey(processDefinitionKey)
                               .taskAssignee(assignee_finance)
                               .singleResult();
        if(task == null) {
            log.info("finance无待执行任务");
            return;
        }
        //    根据任务id完成任务
        taskService.complete(task.getId());
        log.info("finance{}任务完成", task.getId());
    }

}
