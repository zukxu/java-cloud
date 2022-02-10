package com.zukxu.flowable.service;

import com.zukxu.flowable.FlowServiceFactory;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 流程定义
 * </p>
 *
 * @author xupu
 * @since 2022-02-10 15:12
 */
@Service
public class FlowableDefinitionService extends FlowServiceFactory {
    /**
     * 部署流程定义
     *
     * @return
     */
    public String deployDefinition() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("flowable/holiday-request.bpmn20.xml")
                .deploy();
        return "";
    }
}
