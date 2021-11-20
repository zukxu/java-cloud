package com.zukxu.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * activiti 建表
 *
 * @author xupu
 * @description
 * @date 2021/10/23 12:01:34
 */
@SpringBootTest
public class TestCreateTable {

    /**
     * 生成activiti 的数据库表
     */
    @Test
    void createTable() {
        //默认创建方式
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //通用创建方式,指定文件名进行创建
        //ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml","processEngineConfiguration");
        //processEngineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }

}
