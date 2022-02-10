package com.zukxu.flowable.service;

import com.github.pagehelper.PageInfo;
import com.zukxu.common.result.R;
import com.zukxu.common.result.RMap;
import com.zukxu.flowable.FlowServiceFactory;
import com.zukxu.flowable.dto.ProcessDefinitionDTO;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        rMap.put("id", processDefinition.getId())//id=key:version:deploymentId
                .put("name", processDefinition.getName())
                .put("key", processDefinition.getKey())
                .put("category", processDefinition.getCategory())
                .put("version", processDefinition.getVersion())
                .put("deployId", processDefinition.getDeploymentId())
                .put("description", processDefinition.getDescription())
                .put("suspensionState", processDefinition.isSuspended())
                .put("tenantId", processDefinition.getTenantId());
        return R.ok(rMap);
    }

    /**
     * 查询流程定义列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public R<?> listDeploy(Integer pageNum, Integer pageSize) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        PageInfo<ProcessDefinitionDTO> pageInfo = new PageInfo<>();
        pageInfo.setTotal(processDefinitionQuery.count());
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.orderByProcessDefinitionCategory().asc().listPage(pageNum - 1, pageSize);
        List<ProcessDefinitionDTO> dataList = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            //获取部署相关信息
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            ProcessDefinitionDTO procDefDTO = new ProcessDefinitionDTO();
            BeanUtils.copyProperties(processDefinition, procDefDTO);
            //根据部署获取绑定的表单
           /* SysForm sysForm = sysFormDeployService.selectByDeployId(deploymentId);
            if (Objects.nonNull(sysForm)) {
                procDefDTO.setFormName(sysForm.getFormName());
                procDefDTO.setFormId(sysForm.getFormId());
            }*/
            // 流程定义时间
            procDefDTO.setDeploymentTime(deployment.getDeploymentTime());
            dataList.add(procDefDTO);
        }
        pageInfo.setList(dataList);
        return R.ok(pageInfo);
    }
}
