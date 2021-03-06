package com.zukxu.flowable.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zukxu.common.constant.Const;
import com.zukxu.common.utils.ServletUtils;
import com.zukxu.flowable.common.InitUtils;
import com.zukxu.flowable.common.constant.ProcessConstants;
import com.zukxu.flowable.common.entity.SysUser;
import com.zukxu.flowable.common.enums.FlowComment;
import com.zukxu.flowable.model.dto.ActProcDefDTO;
import com.zukxu.flowable.model.dto.FlowCategoryDTO;
import com.zukxu.flowable.model.dto.ProcessDefinitionDTO;
import com.zukxu.flowable.model.entity.SysForm;
import com.zukxu.flowable.factory.FlowServiceFactory;
import com.zukxu.flowable.mapper.FlowDefinitionMapper;
import com.zukxu.flowable.service.FlowDefinitionService;
import com.zukxu.flowable.service.SysFormDeployService;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-15 20:12
 */
@Service
public class FlowDefinitionServiceImpl extends FlowServiceFactory implements FlowDefinitionService {
    @Autowired
    private FlowDefinitionMapper flowDefinitionMapper;
    @Autowired
    private SysFormDeployService sysFormDeployService;

    @Override
    public PageInfo<ActProcDefDTO> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ActProcDefDTO> pageInfo = new PageInfo<>();
        // ??????????????????????????????
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                //.latestVersion()
                .orderByProcessDefinitionKey().asc();
        pageInfo.setTotal(processDefinitionQuery.count());

        //????????????????????????
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(pageNum - 1, pageSize);

        List<ActProcDefDTO> dataList = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            //????????????????????????
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            ActProcDefDTO procDefDTO = new ActProcDefDTO();
            BeanUtils.copyProperties(processDefinition, procDefDTO);
            //?????????????????????????????????
            SysForm sysForm = sysFormDeployService.selectByDeployId(deploymentId);
            if (Objects.nonNull(sysForm)) {
                procDefDTO.setFormName(sysForm.getFormName());
                procDefDTO.setFormId(sysForm.getFormId());
            }
            // ??????????????????
            procDefDTO.setDeploymentTime(deployment.getDeploymentTime());
            dataList.add(procDefDTO);
        }
        pageInfo.setList(dataList);
        return pageInfo;
    }

    @Override
    public void importFile(String name, String category, InputStream in) {
        category = flowDefinitionMapper.selectCategoryName(category);
        Deployment deploy = repositoryService.createDeployment().addInputStream(name + ProcessConstants.BPMN_FILE_SUFFIX, in).name(name).category(category).deploy();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        repositoryService.setProcessDefinitionCategory(definition.getId(), category);
    }

    public PageInfo<ProcessDefinitionDTO> listProcessDefinition(ProcessDefinitionDTO processDefinition) {
        Integer pageNum = ServletUtils.getParameterToInt("pageNum");
        Integer pageSize = ServletUtils.getParameterToInt("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ProcessDefinitionDTO> pageInfo = new PageInfo<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.orderByProcessDefinitionId().orderByProcessDefinitionVersion().desc();
        if (StrUtil.isNotBlank(processDefinition.getName())) {
            processDefinitionQuery.processDefinitionNameLike("%" + processDefinition.getName() + "%");
        }
        if (StrUtil.isNotBlank(processDefinition.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike("%" + processDefinition.getKey() + "%");
        }
        if (StrUtil.isNotBlank(processDefinition.getCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike("%" + processDefinition.getCategory() + "%");
        }

        List<ProcessDefinition> processDefinitionList;
        List<ProcessDefinitionDTO> dtoList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(pageNum) && ObjectUtil.isNotEmpty(pageSize)) {
            processDefinitionList = processDefinitionQuery.listPage((pageNum - 1) * pageSize, pageSize);
            pageInfo.setTotal(processDefinitionQuery.count());
        } else {
            processDefinitionList = processDefinitionQuery.list();
        }
        for (ProcessDefinition definition : processDefinitionList) {
            ProcessDefinitionEntityImpl entityImpl = (ProcessDefinitionEntityImpl) definition;
            ProcessDefinitionDTO entity = new ProcessDefinitionDTO();
            entity.setId(definition.getId());
            entity.setKey(definition.getKey());
            entity.setName(definition.getName());
            entity.setCategory(definition.getCategory());
            entity.setVersion(definition.getVersion());
            entity.setDescription(definition.getDescription());
            entity.setDeploymentId(definition.getDeploymentId());
            Deployment deployment = repositoryService.createDeploymentQuery()
                    .deploymentId(definition.getDeploymentId())
                    .singleResult();
            entity.setDeploymentTime(deployment.getDeploymentTime());
            entity.setDiagramResourceName(definition.getDiagramResourceName());
            entity.setResourceName(definition.getResourceName());
            entity.setSuspendState(entityImpl.getSuspensionState() + "");
            if (entityImpl.getSuspensionState() == 1) {
                entity.setSuspendStateName("?????????");
            } else {
                entity.setSuspendStateName("?????????");
            }
            dtoList.add(entity);
        }
        pageInfo.setList(dtoList);
        return pageInfo;
    }

    @Override
    public String readXml(String deployId) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        InputStream inputStream = repositoryService.getResourceAsStream(definition.getDeploymentId(), definition.getResourceName());
        String result = IoUtil.read(inputStream, StandardCharsets.UTF_8);
        //.toString(inputStream, StandardCharsets.UTF_8.name());
        return result;
    }

    @Override
    public InputStream readImage(String deployId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        //???????????????
        DefaultProcessDiagramGenerator diagramGenerator = new DefaultProcessDiagramGenerator();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        //???????????????
        return diagramGenerator.generatePngDiagram(bpmnModel, false);
    }

    @Override
    public String startProcessInstanceById(String procDefId, Map<String, Object> variables) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId)
                    .latestVersion().singleResult();
            if (Objects.nonNull(processDefinition) && processDefinition.isSuspended()) {
                return "??????????????????,??????????????????";
            }
            // ?????????????????????Id????????????
            SysUser sysUser = InitUtils.getUserByUserId("1");
            identityService.setAuthenticatedUserId(sysUser.getId());
            variables.put(ProcessConstants.PROCESS_INITIATOR, "");
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId, variables);
            // ?????????????????????????????????????????????????????????
            // TODO ?????????????????????????????????????????????????????????
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
            if (ObjectUtil.isNotEmpty(task)) {
                taskService.addComment(task.getId(), processInstance.getProcessInstanceId(), FlowComment.NORMAL.getType(), sysUser.getUserName() + "??????????????????");
                taskService.complete(task.getId(), variables);
            }
            return Const.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return "??????????????????";
        }
    }

    @Override
    public void updateState(Integer state, String deployId) {
        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
        // ??????
        if (state == 1) {
            repositoryService.activateProcessDefinitionById(procDef.getId(), true, null);
        }
        // ??????
        if (state == 2) {
            repositoryService.suspendProcessDefinitionById(procDef.getId(), true, null);
        }
    }

    @Override
    public void delete(String deployId) {
        //true ???????????????
        repositoryService.deleteDeployment(deployId, true);
    }

    @Override
    public List<FlowCategoryDTO> listCategory() {
        return flowDefinitionMapper.listCategory();
    }
}
