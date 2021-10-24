package com.zukxu.activiti.service;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * act流程业务
 *
 * @author xupu
 * @date 2021/10/24 20:58:19
 */
public class ActService {


    /**
     * 部署流程 文件上传方式
     */
    private void deployments() {
        //1、创建DefaultProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3、进行部署
        Deployment deployment = repositoryService.createDeployment()
                                                 .addClasspathResource("bpmn/Leave.bpmn")
                                                 .addClasspathResource("bpmn/Leave.png")
                                                 .name("请假流程").deploy();
        //4、输出部署信息
        System.out.println("流程部署id:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());
    }

    /**
     * 部署流程 zip压缩文件上传方式
     */
    private void deploymentsByZip() {
        //1、定义zip输入流
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("bpmn/Leave.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);

        //2、获取RepositoryService实例
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3、进行部署
        Deployment deployment = repositoryService.createDeployment()
                                                 .addZipInputStream(zipInputStream)
                                                 .deploy();
        //4、输出部署信息
        System.out.println("流程部署id:" + deployment.getId());
        System.out.println("流程部署Name:" + deployment.getName());
    }

    /**
     * 启动流程实例
     */
    private void task() {
        //1、创建DefaultProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取RuntimeService实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3、进行启动实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myLeave");
        //4、输出实例内容
        System.out.println("流程定义id:" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id:" + processInstance.getId());
        System.out.println("当前活动id:" + processInstance.getActivityId());
    }

    /**
     * 查询当前个人待执行任务
     */
    private void findPersonalTaskList() {
        //1、当前任务负责人
        String assignee = "worker";
        //1、创建DefaultProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取TaskService
        TaskService taskService = processEngine.getTaskService();
        //3、根据流程key和任务负责人进行查询任务
        List<Task> list = taskService.createTaskQuery()
                                     .processDefinitionKey("myLeave")
                                     .taskAssignee(assignee)
                                     .list();
        for(Task task : list) {
            System.out.println("流程实例id:" + task.getProcessInstanceId());
            System.out.println("任务id:" + task.getId());
            System.out.println("任务负责人任务id:" + task.getAssignee());
            System.out.println("任务名称:" + task.getName());
        }
    }

    /**
     * 完成任务
     */
    private void completeTask() {
        //1、创建DefaultProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取TaskService
        TaskService taskService = processEngine.getTaskService();
        //3、根据流程key 任务负责人查询对象
        //返回一个任务对象
        Task task = taskService.createTaskQuery().processDefinitionKey("myLeave").taskAssignee("worker").singleResult();
        //完成任务,完成之后会进入下一环节
        taskService.complete(task.getId());


    }

    /**
     * 查询已经定义的流程
     */
    private void findProcessDefinition() {
        //定义流程key
        String processKey = "myLeave";
        //1、创建DefaultProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
                                                   .processDefinitionKey(processKey)
                                                   .list();
        //输出想看的参数信息
    }

    /**
     * 删除流程定义
     */

    private void deleteDefinition() {
        //流程部署id
        String processId = "1";
        //1、创建DefaultProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取RuntimeService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除流程定义，但是如果该流程中还有流程实例处于启动中状态则删除错误
        repositoryService.deleteDeployment(processId);
        //设置true参数，会进行级联删除，即使存在启动中状态的实例也会删除，设置为false为非级联删除
        //也会将所有的历史记录进行删除
        //repositoryService.deleteDeployment(processId,true);

    }

    /**
     * 查询历史信息
     */
    private void findHistory() {
        //1、创建DefaultProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //2、获取HistoryService
        HistoryService historyService = processEngine.getHistoryService();
        //获取actinst表的查询对象
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        //有多种查询方法
        instanceQuery.processInstanceId("2501");
        instanceQuery.activityInstanceId("");
    }

}
