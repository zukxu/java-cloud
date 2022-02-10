package com.zukxu.flowable.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zukxu.common.result.R;
import com.zukxu.common.result.RMap;
import com.zukxu.flowable.FlowServiceFactory;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public R<?> deployDefinition() {
       /* Deployment deploy = repositoryService.createDeployment()
                .addInputStream(name + ProcessConstants.BPMN_FILE_SUFFIX, in)
                .name(name)
                .category(category)
                .deploy();*/
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("flowable/holiday-request.bpmn20.xml")
                .deploy();
        return R.ok(deployment);
    }

    /**
     * 查询部署的流程定义
     *
     * @param deployId
     *
     */
    public R<?> getDeployDetails(String deployId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
        RMap rMap = new RMap();
        rMap.put("id",processDefinition.getId())//id=key:version:deploymentId
                .put("key",processDefinition.getKey())
                .put("version",processDefinition.getVersion())
                .put("deployId",processDefinition.getDeploymentId())
                .put("name",processDefinition.getName())
                .put("description",processDefinition.getDescription())
                .put("category",processDefinition.getCategory())
                .put("tenantId",processDefinition.getTenantId());
        return R.ok(rMap);
    }

    /**
     * 查询流程定义列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public R<?> listDeploy(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        return R.ok(new PageInfo<>(list));
    }
}
