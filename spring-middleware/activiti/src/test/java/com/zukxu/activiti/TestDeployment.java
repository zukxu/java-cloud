package com.zukxu.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * demo
 *
 * @author xupu
 * @date 2021/11/20 17:31:07
 */
@Slf4j
@SpringBootTest
public class TestDeployment {

    /**
     * 分别上传流程定义文件和流程示意图进行部署
     */
    @Test
    void testDeploy() {
        //    创建processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //    使用repositoryService进行部署
        Deployment deployment = repositoryService.createDeployment()
                                                 .addClasspathResource("bpmn/Leave-1.bpmn20.xml")//添加流程定义文件
                                                 .addClasspathResource("bpmn/Leave-1.png")//添加流程png资源
                                                 .name("员工请假流程Leave")//定义部署名称
                                                 .deploy();
        //    输出部署信息
        log.info("流程部署id：{}", deployment.getId());
        log.info("流程部署名称：{}", deployment.getName());
    }

    /**
     * 将流程文件和图片打包一起部署
     */
    @Test
    void deploymentsByZip() {
        //    定义上传文件流
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("bpmn/Leave-1.zip");
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //    创建processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //    获取RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //    使用repositoryService进行部署
        Deployment deployment = repositoryService.createDeployment()
                                                 .addZipInputStream(zipInputStream)
                                                 .deploy();
        //    输出部署信息
        log.info("流程部署id：{}", deployment.getId());
        log.info("流程部署名称：{}", deployment.getName());
    }

}
