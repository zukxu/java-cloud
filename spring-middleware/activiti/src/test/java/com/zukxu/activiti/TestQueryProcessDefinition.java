package com.zukxu.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author xupu
 * @date 2021/11/20 22:50:06
 */
@Slf4j
@SpringBootTest
public class TestQueryProcessDefinition {
    //private final String key = "Leave-1";
    private final String key = "evection-variable";
    /**
     * 查询一个文件中有几个流程定义
     */
    @Test
    void queryProcessDefinition() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.processDefinitionKey(key).list();
        for(ProcessDefinition processDefinition : processDefinitionList) {
            log.info("流程定义id：{}", processDefinition.getId());
            log.info("流程定义name:{}", processDefinition.getName());
            log.info("流程定义key:{}", processDefinition.getKey());
            log.info("流程定义version:{}", processDefinition.getVersion());
            log.info("流程部署id:{}", processDefinition.getDeploymentId());
        }
    }

    /**
     * 查询当前的流程定义中有几个流程实例运行
     */
    @Test
    void queryProcessInstance() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery()
                                                                  .processDefinitionKey(key)
                                                                  .list();
        for(ProcessInstance processInstance : processInstanceList) {
            log.info("流程实例id：{}", processInstance.getProcessInstanceId());
            log.info("所属流程定义id：{}", processInstance.getProcessDefinitionId());
            log.info("是否执行完毕：{}", processInstance.isEnded());
            log.info("是否暂停执行：{}", processInstance.isSuspended());
            log.info("当前活动标识:{}", processInstance.getActivityId());
            log.info("业务关键字:{}", processInstance.getBusinessKey());
        }
    }

    /**
     * 查询当前的流程定义中有几个流程实例运行 历史记录
     */
    @Test
    void queryHistoryInfo() {
        //Leave-1
        //String instanceId = "2501";
        //String processDefinitionId = "Leave-1:1:4";

        //evection-variable
        //String instanceId = "30001";
        String instanceId = "32501";
        String processDefinitionId = "evection-variable:1:25004";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        //获取actinst的查询对象
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        //查询act_hi_actinst 表 根据InstanceId 进行查询，查询一个流程的所有历史信息
        instanceQuery.processInstanceId(instanceId);
        //查询act_hi_actinst 表 根据DefinitionId进行查询，查询一个流程的所有历史信息
        instanceQuery.processDefinitionId(processDefinitionId);
        //排序 根据开始时间排序
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        //查询所有的list
        List<HistoricActivityInstance> historicProcessInstanceList = instanceQuery.list();
        //输出信息
        for(HistoricActivityInstance instance : historicProcessInstanceList) {
            log.info("当前活动标识:{}", instance.getActivityId());
            log.info("当前活动名称:{}", instance.getActivityName());
            log.info("所属流程定义id：{}", instance.getProcessDefinitionId());
            log.info("所属流程实例id：{}", instance.getProcessInstanceId());
            System.out.println("==============================================");
        }
    }

}
