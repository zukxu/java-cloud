package com.zukxu.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xupu
 * @date 2021/11/21 22:05:20
 */
@Slf4j
@SpringBootTest
public class TestBusinessKey {

    /**
     * 传递BusinessKey
     */
    @Test
    void testBusinessKey() {
        //    启动流程实例的过程中添加一个businessKey
        //    例如出差申请的id
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("Leave-1", "1001");
        log.info("BusinessKey:{}",instance.getBusinessKey());
    }
    /**
     * 传递 流程变量
     */
    @Test
    void testVariables() {
        //    启动流程实例的过程中添加一个businessKey
        //    例如出差申请的id
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("Leave-1", "1001");
        log.info("BusinessKey:{}",instance.getBusinessKey());
    }

}
