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
 * @date 2021/11/20 17:59:57
 */
@Slf4j
@SpringBootTest
public class TestStartProcess {

    /**
     * 启动流程实例
     */
    @Test
    void testStartProcess() {
        //    创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //    根据流程定义id进行启动流程
        String key = "Leave-1";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        //    输出启动信息
        log.info("流程定义id:{}", processInstance.getProcessDefinitionId());
        log.info("流程实例id:{}", processInstance.getId());
        log.info("当前活动id:{}", processInstance.getActivityId());
    }

}
