package com.zukxu.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.junit.jupiter.api.Test;

/**
 * @author xupu
 * @description
 * @date 2021/10/23 12:01:34
 */
public class CreateTable {

    @Test
    void createTable() {
        //默认创建方式
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }

}
