package com.zukxu.activiti;

import com.zukxu.activiti.model.Evection;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * 启动流程实例,传递流程变量
     */
    @Test
    void testStartProcessVariables() {
        //    创建ProcessEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取RuntimeService
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //    根据流程定义id进行启动流程
        String key = "Leave-variable";
        //设置流程变量
        Map<String, Object> variables = new HashMap<>();
        Evection evection = new Evection();
        //设置出差日期
        evection.setNum(2);
        //设置任务执行人
        variables.put("evection", evection);
        variables.put("assign0", "李四");
        variables.put("assign1", "王经理");
        variables.put("assign2", "张财务");
        variables.put("assign3", "杨总经理");
        //启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key,variables);
        //    输出启动信息
        log.info("流程定义id:{}", processInstance.getProcessDefinitionId());
        log.info("流程实例id:{}", processInstance.getId());
        log.info("当前活动id:{}", processInstance.getActivityId());
    }

}
