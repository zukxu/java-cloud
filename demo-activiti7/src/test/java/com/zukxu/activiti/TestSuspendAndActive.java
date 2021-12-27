package com.zukxu.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xupu
 * @date 2021/11/21 22:14:31
 */
@Slf4j
@SpringBootTest
public class TestSuspendAndActive {

    private final String key = "Leave-1";


    /**
     * 挂起当前流程定义下的所有的流程实例
     */
    @Test
    void suspendAllProcessInstances() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                               .processDefinitionKey(key)
                                                               .singleResult();
        //    获取当前定义下的实例是否都是挂起状态
        boolean suspended = processDefinition.isSuspended();
        //    获取流程定义id
        String definitionId = processDefinition.getId();
        //    如果是挂起状态，修改为激活状态，反之亦然
        if(suspended){
            /**
             * @param1 definitionId
             * @param2 是否激活
             * @param3 激活时间
             */
            repositoryService.activateProcessDefinitionById(definitionId,true,null);
            log.info("流程{}已激活",definitionId);
        }else{
            /**
             * @param1 definitionId
             * @param2 是否挂起
             * @param3 挂起暂停时间
             */
            repositoryService.suspendProcessDefinitionById(definitionId,true,null);
            log.info("流程{}已暂停",definitionId);
        }
    }

    /**
     * 挂起单独的流程实例
     */
    @Test
    void suspendSingleProcessInstance(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                                                        .processInstanceId("12501")
                                                        .singleResult();
        boolean suspended = instance.isSuspended();
        String instanceId = instance.getId();
        if(suspended) {
            runtimeService.activateProcessInstanceById(instanceId);
            log.info("流程{}已激活",instanceId);
        }else{
            runtimeService.suspendProcessInstanceById(instanceId);
            log.info("流程{}已暂停",instanceId);
        }
    }

}
