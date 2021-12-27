package com.zukxu.activiti;

import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author xupu
 * @date 2021/11/20 23:02:38
 */
@Slf4j
@SpringBootTest
public class TestDelDeploy {

    /**
     * 删除流程部署
     */
    @Test
    void deleteDeployment() {
        String deployId = "17501";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除流程定义，如果该部署已有流程实例启动则删除报错
        repositoryService.deleteDeployment(deployId);
        //删除流程定义，设置true，级联删除即使该部署已有流程实例启动也可以删除,同时删除日志，一般只会开放权限给管理员
        //repositoryService.deleteDeployment(deployId,true);
        log.info("删除完毕！");
    }

    /**
     * 查询已有的bpmn信息
     *
     * @throws Exception
     */
    @Test
    void queryBpmnFiles() throws Exception {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                               .processDefinitionKey("Leave-1")
                                                               .singleResult();
        //获取部署id
        String deploymentId = processDefinition.getDeploymentId();
        //    通过方法读取数据库中的文件信息
        InputStream pngInput = repositoryService.getResourceAsStream(deploymentId,
                                                                     processDefinition.getDiagramResourceName());
        InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId,
                                                                      processDefinition.getResourceName());

        //    导出文件
        File png = new File("d:/Leave.png");
        File bpmn = new File("d:/Leave.bpmn20.xml");

        FileOutputStream pngOut = new FileOutputStream(png);
        FileOutputStream bpmnOut = new FileOutputStream(bpmn);
        //输入流和输出流的转换
        IoUtil.copy(pngInput, pngOut);
        IoUtil.copy(bpmnInput, bpmnOut);

        //关闭流
        pngOut.close();
        bpmnOut.close();
        pngInput.close();
        bpmnInput.close();
    }

}
