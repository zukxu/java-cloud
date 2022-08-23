package com.zukxu.flowable.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zukxu.common.exception.GlobalException;
import com.zukxu.common.result.R;
import com.zukxu.flowable.common.InitUtils;
import com.zukxu.flowable.common.constant.ProcessConstants;
import com.zukxu.flowable.common.entity.SysRole;
import com.zukxu.flowable.common.entity.SysUser;
import com.zukxu.flowable.common.enums.FlowComment;
import com.zukxu.flowable.config.MyProcessDiagramGenerator;
import com.zukxu.flowable.factory.FlowFactory;
import com.zukxu.flowable.model.dto.FlowCommentDTO;
import com.zukxu.flowable.model.dto.FlowNextDTO;
import com.zukxu.flowable.model.dto.FlowTaskDTO;
import com.zukxu.flowable.model.dto.FlowViewerDTO;
import com.zukxu.flowable.model.entity.SysForm;
import com.zukxu.flowable.model.vo.FlowTaskVo;
import com.zukxu.flowable.service.FlowTaskService;
import com.zukxu.flowable.service.SysFormDeployService;
import com.zukxu.flowable.utils.FindNextNodeUtil;
import com.zukxu.flowable.utils.FlowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.util.CollectionUtil;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author XuanXuan
 * @date 2021-04-03
 **/
@Service
@Slf4j
public class FlowTaskServiceImpl extends FlowFactory implements FlowTaskService {

    //@Resource
    //private SysUserService sysUserService;
    //
    //@Resource
    //private SysRoleService sysRoleService;


    @Resource
    private SysFormDeployService sysFormDeployService;

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * 完成任务
     *
     * @param taskVo 请求实体参数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R<?> complete(FlowTaskVo taskVo) {
        Task task = taskService.createTaskQuery().taskId(taskVo.getTaskId()).singleResult();
        if(Objects.isNull(task)) {
            return R.fail("任务不存在");
        }
        if(DelegationState.PENDING.equals(task.getDelegationState())) {
            taskService.addComment(taskVo.getTaskId(), taskVo.getInstanceId(), FlowComment.DELEGATE.getType(), taskVo.getComment());
            taskService.resolveTask(taskVo.getTaskId(), taskVo.getValues());
        } else {
            taskService.addComment(taskVo.getTaskId(), taskVo.getInstanceId(), FlowComment.NORMAL.getType(), taskVo.getComment());
            String userId = "1";
            taskService.setAssignee(taskVo.getTaskId(), userId);
            taskService.complete(taskVo.getTaskId(), taskVo.getValues());
        }
        return R.ok();
    }

    /**
     * 驳回任务
     *
     * @param flowTaskVo
     */
    @Override
    public void taskReject(FlowTaskVo flowTaskVo) {
        if(taskService.createTaskQuery().taskId(flowTaskVo.getTaskId()).singleResult().isSuspended()) {
            throw new GlobalException("任务处于挂起状态");
        }
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(flowTaskVo.getTaskId()).singleResult();
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                               .processDefinitionId(task.getProcessDefinitionId())
                                                               .singleResult();
        // 获取所有节点信息
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        // 获取全部节点列表，包含子节点
        Collection<FlowElement> allElements = FlowableUtils.getAllElements(process.getFlowElements(), null);
        // 获取当前任务节点元素
        FlowElement source = null;
        if(allElements != null) {
            for(FlowElement flowElement : allElements) {
                // 类型为用户节点
                if(flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    // 获取节点信息
                    source = flowElement;
                }
            }
        }

        // 目的获取所有跳转到的节点 targetIds
        // 获取当前节点的所有父级用户任务节点
        // 深度优先算法思想：延边迭代深入
        List<UserTask> parentUserTaskList = FlowableUtils.iteratorFindParentUserTasks(source, null, null);
        if(parentUserTaskList == null || parentUserTaskList.size() == 0) {
            throw new GlobalException("当前节点为初始任务节点，不能驳回");
        }
        // 获取活动 ID 即节点 Key
        List<String> parentUserTaskKeyList = new ArrayList<>();
        parentUserTaskList.forEach(item -> parentUserTaskKeyList.add(item.getId()));
        // 获取全部历史节点活动实例，即已经走过的节点历史，数据采用开始时间升序
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                                                                            .processInstanceId(task.getProcessInstanceId())
                                                                            .orderByHistoricTaskInstanceStartTime()
                                                                            .asc()
                                                                            .list();
        // 数据清洗，将回滚导致的脏数据清洗掉
        List<String> lastHistoricTaskInstanceList = FlowableUtils.historicTaskInstanceClean(allElements, historicTaskInstanceList);
        // 此时历史任务实例为倒序，获取最后走的节点
        List<String> targetIds = new ArrayList<>();
        // 循环结束标识，遇到当前目标节点的次数
        int number = 0;
        StringBuilder parentHistoricTaskKey = new StringBuilder();
        for(String historicTaskInstanceKey : lastHistoricTaskInstanceList) {
            // 当会签时候会出现特殊的，连续都是同一个节点历史数据的情况，这种时候跳过
            if(parentHistoricTaskKey.toString().equals(historicTaskInstanceKey)) {
                continue;
            }
            parentHistoricTaskKey = new StringBuilder(historicTaskInstanceKey);
            if(historicTaskInstanceKey.equals(task.getTaskDefinitionKey())) {
                number++;
            }
            // 在数据清洗后，历史节点就是唯一一条从起始到当前节点的历史记录，理论上每个点只会出现一次
            // 在流程中如果出现循环，那么每次循环中间的点也只会出现一次，再出现就是下次循环
            // number == 1，第一次遇到当前节点
            // number == 2，第二次遇到，代表最后一次的循环范围
            if(number == 2) {
                break;
            }
            // 如果当前历史节点，属于父级的节点，说明最后一次经过了这个点，需要退回这个点
            if(parentUserTaskKeyList.contains(historicTaskInstanceKey)) {
                targetIds.add(historicTaskInstanceKey);
            }
        }

        // 目的获取所有需要被跳转的节点 currentIds
        // 取其中一个父级任务，因为后续要么存在公共网关，要么就是串行公共线路
        UserTask oneUserTask = parentUserTaskList.get(0);
        // 获取所有正常进行的任务节点 Key，这些任务不能直接使用，需要找出其中需要撤回的任务
        List<Task> runTaskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        List<String> runTaskKeyList = new ArrayList<>();
        runTaskList.forEach(item -> runTaskKeyList.add(item.getTaskDefinitionKey()));
        // 需驳回任务列表
        List<String> currentIds = new ArrayList<>();
        // 通过父级网关的出口连线，结合 runTaskList 比对，获取需要撤回的任务
        List<UserTask> currentUserTaskList = FlowableUtils.iteratorFindChildUserTasks(oneUserTask, runTaskKeyList, null, null);
        currentUserTaskList.forEach(item -> currentIds.add(item.getId()));

        // 规定：并行网关之前节点必须需存在唯一用户任务节点，如果出现多个任务节点，则并行网关节点默认为结束节点，原因为不考虑多对多情况
        if(targetIds.size() > 1 && currentIds.size() > 1) {
            throw new GlobalException("任务出现多对多情况，无法撤回");
        }

        // 循环获取那些需要被撤回的节点的ID，用来设置驳回原因
        List<String> currentTaskIds = new ArrayList<>();
        currentIds.forEach(currentId -> runTaskList.forEach(runTask -> {
            if(currentId.equals(runTask.getTaskDefinitionKey())) {
                currentTaskIds.add(runTask.getId());
            }
        }));
        // 设置驳回意见
        currentTaskIds.forEach(item -> taskService.addComment(item,
                                                              task.getProcessInstanceId(),
                                                              FlowComment.REJECT.getType(),
                                                              flowTaskVo.getComment()));

        try {
            // 如果父级任务多于 1 个，说明当前节点不是并行节点，原因为不考虑多对多情况
            if(targetIds.size() > 1) {
                // 1 对 多任务跳转，currentIds 当前节点(1)，targetIds 跳转到的节点(多)
                runtimeService.createChangeActivityStateBuilder()
                              .processInstanceId(task.getProcessInstanceId()).
                              moveSingleActivityIdToActivityIds(currentIds.get(0), targetIds).changeState();
            }
            // 如果父级任务只有一个，因此当前任务可能为网关中的任务
            if(targetIds.size() == 1) {
                // 1 对 1 或 多 对 1 情况，currentIds 当前要跳转的节点列表(1或多)，targetIds.get(0) 跳转到的节点(1)
                runtimeService.createChangeActivityStateBuilder()
                              .processInstanceId(task.getProcessInstanceId())
                              .moveActivityIdsToSingleActivityId(currentIds, targetIds.get(0)).changeState();
            }
        } catch(FlowableObjectNotFoundException e) {
            throw new GlobalException("未找到流程实例，流程可能已发生变化");
        } catch(FlowableException e) {
            throw new GlobalException("无法取消或开始活动");
        }

    }

    /**
     * 退回任务
     *
     * @param flowTaskVo 请求实体参数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void taskReturn(FlowTaskVo flowTaskVo) {
        if(taskService.createTaskQuery().taskId(flowTaskVo.getTaskId()).singleResult().isSuspended()) {
            throw new GlobalException("任务处于挂起状态");
        }
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(flowTaskVo.getTaskId()).singleResult();
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                               .processDefinitionId(task.getProcessDefinitionId())
                                                               .singleResult();
        // 获取所有节点信息
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        // 获取全部节点列表，包含子节点
        Collection<FlowElement> allElements = FlowableUtils.getAllElements(process.getFlowElements(), null);
        // 获取当前任务节点元素
        FlowElement source = null;
        // 获取跳转的节点元素
        FlowElement target = null;
        if(allElements != null) {
            for(FlowElement flowElement : allElements) {
                // 当前任务节点元素
                if(flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    source = flowElement;
                }
                // 跳转的节点元素
                if(flowElement.getId().equals(flowTaskVo.getTargetKey())) {
                    target = flowElement;
                }
            }
        }

        // 从当前节点向前扫描
        // 如果存在路线上不存在目标节点，说明目标节点是在网关上或非同一路线上，不可跳转
        // 否则目标节点相对于当前节点，属于串行
        Boolean isSequential = FlowableUtils.iteratorCheckSequentialReferTarget(source, flowTaskVo.getTargetKey(), null, null);
        if(!isSequential) {
            throw new GlobalException("当前节点相对于目标节点，不属于串行关系，无法回退");
        }

        // 获取所有正常进行的任务节点 Key，这些任务不能直接使用，需要找出其中需要撤回的任务
        List<Task> runTaskList = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        List<String> runTaskKeyList = new ArrayList<>();
        runTaskList.forEach(item -> runTaskKeyList.add(item.getTaskDefinitionKey()));
        // 需退回任务列表
        List<String> currentIds = new ArrayList<>();
        // 通过父级网关的出口连线，结合 runTaskList 比对，获取需要撤回的任务
        List<UserTask> currentUserTaskList = FlowableUtils.iteratorFindChildUserTasks(target, runTaskKeyList, null, null);
        currentUserTaskList.forEach(item -> currentIds.add(item.getId()));

        // 循环获取那些需要被撤回的节点的ID，用来设置驳回原因
        List<String> currentTaskIds = new ArrayList<>();
        currentIds.forEach(currentId -> runTaskList.forEach(runTask -> {
            if(currentId.equals(runTask.getTaskDefinitionKey())) {
                currentTaskIds.add(runTask.getId());
            }
        }));
        // 设置回退意见
        for(String currentTaskId : currentTaskIds) {
            taskService.addComment(currentTaskId, task.getProcessInstanceId(), FlowComment.REBACK.getType(), flowTaskVo.getComment());
        }

        try {
            // 1 对 1 或 多 对 1 情况，currentIds 当前要跳转的节点列表(1或多)，targetKey 跳转到的节点(1)
            runtimeService.createChangeActivityStateBuilder()
                          .processInstanceId(task.getProcessInstanceId())
                          .moveActivityIdsToSingleActivityId(currentIds, flowTaskVo.getTargetKey()).changeState();
        } catch(FlowableObjectNotFoundException e) {
            throw new GlobalException("未找到流程实例，流程可能已发生变化");
        } catch(FlowableException e) {
            throw new GlobalException("无法取消或开始活动");
        }
    }

    /**
     * 获取所有可回退的节点
     *
     * @param flowTaskVo
     *
     * @return
     */
    @Override
    public R<?> findReturnTaskList(FlowTaskVo flowTaskVo) {
        // 当前任务 task
        Task task = taskService.createTaskQuery().taskId(flowTaskVo.getTaskId()).singleResult();
        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                                                               .processDefinitionId(task.getProcessDefinitionId())
                                                               .singleResult();
        // 获取所有节点信息，暂不考虑子流程情况
        Process process = repositoryService.getBpmnModel(processDefinition.getId()).getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        // 获取当前任务节点元素
        UserTask source = null;
        if(flowElements != null) {
            for(FlowElement flowElement : flowElements) {
                // 类型为用户节点
                if(flowElement.getId().equals(task.getTaskDefinitionKey())) {
                    source = (UserTask) flowElement;
                }
            }
        }
        // 获取节点的所有路线
        List<List<UserTask>> roads = FlowableUtils.findRoad(source, null, null, null);
        // 可回退的节点列表
        List<UserTask> userTaskList = new ArrayList<>();
        for(List<UserTask> road : roads) {
            if(userTaskList.size() == 0) {
                // 还没有可回退节点直接添加
                userTaskList = road;
            } else {
                // 如果已有回退节点，则比对取交集部分
                userTaskList.retainAll(road);
            }
        }
        return R.ok(userTaskList);
    }

    /**
     * 删除任务
     *
     * @param flowTaskVo 请求实体参数
     */
    @Override
    public void deleteTask(FlowTaskVo flowTaskVo) {
        // todo 待确认删除任务是物理删除任务 还是逻辑删除，让这个任务直接通过？
        taskService.deleteTask(flowTaskVo.getTaskId(), flowTaskVo.getComment());
    }

    /**
     * 认领/签收任务
     *
     * @param flowTaskVo 请求实体参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void claim(FlowTaskVo flowTaskVo) {
        taskService.claim(flowTaskVo.getTaskId(), flowTaskVo.getUserId());
    }

    /**
     * 取消认领/签收任务
     *
     * @param flowTaskVo 请求实体参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unClaim(FlowTaskVo flowTaskVo) {
        taskService.unclaim(flowTaskVo.getTaskId());
    }

    /**
     * 委派任务
     *
     * @param flowTaskVo 请求实体参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delegateTask(FlowTaskVo flowTaskVo) {
        taskService.delegateTask(flowTaskVo.getTaskId(), flowTaskVo.getAssignee());
    }

    /**
     * 转办任务
     *
     * @param flowTaskVo 请求实体参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignTask(FlowTaskVo flowTaskVo) {
        taskService.setAssignee(flowTaskVo.getTaskId(), flowTaskVo.getComment());
    }

    /**
     * 我发起的流程
     *
     * @param pageNum
     * @param pageSize
     *
     * @return
     */
    @Override
    public R<?> myProcess(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<FlowTaskDTO> page = new PageInfo<>();
        String userId = "1";
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                                                                                  .startedBy(userId)
                                                                                  .orderByProcessInstanceStartTime()
                                                                                  .desc();
        List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceQuery.listPage(pageNum - 1, pageSize);
        page.setTotal(historicProcessInstanceQuery.count());
        List<FlowTaskDTO> flowList = new ArrayList<>();
        for(HistoricProcessInstance hisIns : historicProcessInstances) {
            FlowTaskDTO flowTask = new FlowTaskDTO();
            flowTask.setCreateTime(hisIns.getStartTime());
            flowTask.setFinishTime(hisIns.getEndTime());
            flowTask.setProcInsId(hisIns.getId());

            // 计算耗时
            if(Objects.nonNull(hisIns.getEndTime())) {
                long time = hisIns.getEndTime().getTime() - hisIns.getStartTime().getTime();
                flowTask.setDuration(getDate(time));
            } else {
                long time = System.currentTimeMillis() - hisIns.getStartTime().getTime();
                flowTask.setDuration(getDate(time));
            }
            // 流程定义信息
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                                                    .processDefinitionId(hisIns.getProcessDefinitionId())
                                                    .singleResult();
            flowTask.setDeployId(pd.getDeploymentId());
            flowTask.setProcDefName(pd.getName());
            flowTask.setProcDefVersion(pd.getVersion());
            flowTask.setCategory(pd.getCategory());
            flowTask.setProcDefVersion(pd.getVersion());
            flowList.add(flowTask);
        }
        page.setList(flowList);
        return R.ok(page);
    }

    /**
     * 取消申请
     *
     * @param flowTaskVo
     *
     * @return
     */
    @Override
    public R<?> stopProcess(FlowTaskVo flowTaskVo) {
        List<Task> task = taskService.createTaskQuery().processInstanceId(flowTaskVo.getInstanceId()).list();
        if(CollectionUtil.isEmpty(task)) {
            throw new GlobalException("流程未启动或已执行完成，取消申请失败");
        }

        SysUser loginUser = InitUtils.getUserByUserId("1");
        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery().processInstanceId(flowTaskVo.getInstanceId()).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        if(Objects.nonNull(bpmnModel)) {
            Process process = bpmnModel.getMainProcess();
            List<EndEvent> endNodes = process.findFlowElementsOfType(EndEvent.class, false);
            if(CollectionUtil.isNotEmpty(endNodes)) {
                Authentication.setAuthenticatedUserId(loginUser.getId());
                String endId = endNodes.get(0).getId();
                List<Execution> executions =
                        runtimeService.createExecutionQuery().parentId(processInstance.getProcessInstanceId()).list();
                List<String> executionIds = new ArrayList<>();
                executions.forEach(execution -> executionIds.add(execution.getId()));
                runtimeService.createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds,
                                                                                                   endId).changeState();
            }
        }

        return R.ok();
    }

    /**
     * 撤回流程  目前存在错误
     *
     * @param flowTaskVo
     *
     * @return
     */
    @Override
    public R<?> revokeProcess(FlowTaskVo flowTaskVo) {
        Task task = taskService.createTaskQuery().processInstanceId(flowTaskVo.getInstanceId()).singleResult();
        if(task == null) {
            throw new GlobalException("流程未启动或已执行完成，无法撤回");
        }

        SysUser loginUser = InitUtils.getUserByUserId("1");
        List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()
                                                           .processInstanceId(task.getProcessInstanceId())
                                                           .orderByTaskCreateTime()
                                                           .asc()
                                                           .list();
        String myTaskId = null;
        HistoricTaskInstance myTask = null;
        for(HistoricTaskInstance hti : htiList) {
            if(loginUser.getId().equals(hti.getAssignee())) {
                myTaskId = hti.getId();
                myTask = hti;
                break;
            }
        }
        if(null == myTaskId) {
            throw new GlobalException("该任务非当前用户提交，无法撤回");
        }

        String processDefinitionId = myTask.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        //变量
        //      Map<String, VariableInstance> variables = runtimeService.getVariableInstances(currentTask.getExecutionId());
        String myActivityId = null;
        List<HistoricActivityInstance> haiList = historyService.createHistoricActivityInstanceQuery()
                                                               .executionId(myTask.getExecutionId()).finished().list();
        for(HistoricActivityInstance hai : haiList) {
            if(myTaskId.equals(hai.getTaskId())) {
                myActivityId = hai.getActivityId();
                break;
            }
        }
        FlowNode myFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(myActivityId);

        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);

        //记录原活动方向
        List<SequenceFlow> oriSequenceFlows = new ArrayList<>(flowNode.getOutgoingFlows());

        return R.ok();
    }


    /**
     * 代办任务列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     *
     * @return
     */
    @Override
    public R<?> todoList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<FlowTaskDTO> page = new PageInfo<>();
        String userId = "1";
        TaskQuery taskQuery = taskService.createTaskQuery()
                                         .active()
                                         .includeProcessVariables()
                                         //                .taskAssignee(userId.toString())
                                         .orderByTaskCreateTime().desc();
        page.setTotal(taskQuery.count());
        List<Task> taskList = taskQuery.listPage(pageNum - 1, pageSize);
        List<FlowTaskDTO> flowList = new ArrayList<>();
        for(Task task : taskList) {
            FlowTaskDTO flowTask = new FlowTaskDTO();
            // 当前流程信息
            flowTask.setTaskId(task.getId());
            flowTask.setTaskDefKey(task.getTaskDefinitionKey());
            flowTask.setCreateTime(task.getCreateTime());
            flowTask.setProcDefId(task.getProcessDefinitionId());
            flowTask.setTaskName(task.getName());
            // 流程定义信息
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                                                    .processDefinitionId(task.getProcessDefinitionId())
                                                    .singleResult();
            flowTask.setDeployId(pd.getDeploymentId());
            flowTask.setProcDefName(pd.getName());
            flowTask.setProcDefVersion(pd.getVersion());
            flowTask.setProcInsId(task.getProcessInstanceId());

            // 流程发起人信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                                                                            .processInstanceId(task.getProcessInstanceId())
                                                                            .singleResult();
            String startUserId = historicProcessInstance.getStartUserId();
            SysUser startUser = InitUtils.getUserByUserId(startUserId);
            flowTask.setStartUserId(startUser.getId());
            flowTask.setStartUserName(startUser.getUserName());
            flowTask.setStartDeptName("自由客");
            flowList.add(flowTask);
        }

        page.setList(flowList);
        return R.ok(page);
    }

    /**
     * 已办任务列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     *
     * @return
     */
    @Override
    public R<?> finishedList(Integer pageNum, Integer pageSize) {
        Page<FlowTaskDTO> page = new Page<>();
        String userId = "1";
        HistoricTaskInstanceQuery taskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                                                                    .includeProcessVariables()
                                                                    .finished()
                                                                    .taskAssignee(userId)
                                                                    .orderByHistoricTaskInstanceEndTime()
                                                                    .desc();
        List<HistoricTaskInstance> historicTaskInstanceList = taskInstanceQuery.listPage(pageNum - 1, pageSize);
        List<FlowTaskDTO> hisTaskList = Lists.newArrayList();
        for(HistoricTaskInstance histTask : historicTaskInstanceList) {
            FlowTaskDTO flowTask = new FlowTaskDTO();
            // 当前流程信息
            flowTask.setTaskId(histTask.getId());
            // 审批人员信息
            flowTask.setCreateTime(histTask.getCreateTime());
            flowTask.setFinishTime(histTask.getEndTime());
            flowTask.setDuration(getDate(histTask.getDurationInMillis()));
            flowTask.setProcDefId(histTask.getProcessDefinitionId());
            flowTask.setTaskDefKey(histTask.getTaskDefinitionKey());
            flowTask.setTaskName(histTask.getName());

            // 流程定义信息
            ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                                                    .processDefinitionId(histTask.getProcessDefinitionId())
                                                    .singleResult();
            flowTask.setDeployId(pd.getDeploymentId());
            flowTask.setProcDefName(pd.getName());
            flowTask.setProcDefVersion(pd.getVersion());
            flowTask.setProcInsId(histTask.getProcessInstanceId());
            flowTask.setHisProcInsId(histTask.getProcessInstanceId());

            // 流程发起人信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                                                                            .processInstanceId(histTask.getProcessInstanceId())
                                                                            .singleResult();
            String startUserId = historicProcessInstance.getStartUserId();
            SysUser startUser = InitUtils.getUserByUserId(startUserId);
            flowTask.setStartUserId(startUser.getId());
            flowTask.setStartUserName(startUser.getUserName());
            flowTask.setStartDeptName("自由客");
            hisTaskList.add(flowTask);
        }
        page.setTotal(hisTaskList.size());
        page.setRecords(hisTaskList);

        return R.ok(page);
    }

    /**
     * 流程历史流转记录
     *
     * @param procInsId 流程实例Id
     *
     * @return
     */
    @Override
    public R<?> flowRecord(String procInsId, String deployId) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(procInsId)) {
            List<HistoricActivityInstance> list = historyService
                    .createHistoricActivityInstanceQuery()
                    .processInstanceId(procInsId)
                    .orderByHistoricActivityInstanceStartTime()
                    .desc().list();
            List<FlowTaskDTO> hisFlowList = new ArrayList<>();
            for(HistoricActivityInstance histIns : list) {
                if(StringUtils.isNotBlank(histIns.getTaskId())) {
                    FlowTaskDTO flowTask = new FlowTaskDTO();
                    flowTask.setTaskId(histIns.getTaskId());
                    flowTask.setTaskName(histIns.getActivityName());
                    flowTask.setCreateTime(histIns.getStartTime());
                    flowTask.setFinishTime(histIns.getEndTime());
                    String assignee = histIns.getAssignee();
                    if(StringUtils.isNotBlank(assignee)) {
                        SysUser sysUser = InitUtils.getUserByUserId(assignee);
                        flowTask.setAssigneeId(sysUser.getId());
                        flowTask.setAssigneeName(sysUser.getUserName());
                        flowTask.setDeptName("自由客");
                    }
                    // 展示审批人员
                    List<HistoricIdentityLink> linksForTask = historyService.getHistoricIdentityLinksForTask(histIns.getTaskId());
                    StringBuilder stringBuilder = new StringBuilder();
                    for(HistoricIdentityLink identityLink : linksForTask) {
                        if("candidate".equals(identityLink.getType())) {
                            String userId = identityLink.getUserId();
                            if(StringUtils.isNotBlank(userId)) {
                                SysUser sysUser = InitUtils.getUserByUserId(userId);
                                stringBuilder.append(sysUser.getUserName()).append(",");
                            }
                            String groupId = identityLink.getGroupId();
                            if(StringUtils.isNotBlank(groupId)) {
                                SysRole sysRole = InitUtils.getRoleById(groupId);
                                stringBuilder.append(sysRole.getRoleName()).append(",");
                            }
                        }
                    }
                    if(StringUtils.isNotBlank(stringBuilder)) {
                        flowTask.setCandidate(stringBuilder.substring(0, stringBuilder.length() - 1));
                    }

                    flowTask.setDuration(histIns.getDurationInMillis() == null || histIns.getDurationInMillis() == 0 ? null : getDate(histIns.getDurationInMillis()));
                    // 获取意见评论内容
                    List<Comment> commentList = taskService.getProcessInstanceComments(histIns.getProcessInstanceId());
                    commentList.forEach(comment -> {
                        if(histIns.getTaskId().equals(comment.getTaskId())) {
                            flowTask.setComment(FlowCommentDTO.builder().type(comment.getType()).comment(comment.getFullMessage()).build());
                        }
                    });
                    hisFlowList.add(flowTask);
                }
            }
            map.put("flowList", hisFlowList);
            //            // 查询当前任务是否完成
            //            List<Task> taskList = taskService.createTaskQuery().processInstanceId(procInsId).list();
            //            if (CollectionUtil.isNotEmpty(taskList)) {
            //                map.put("finished", true);
            //            } else {
            //                map.put("finished", false);
            //            }
        }
        // 第一次申请获取初始化表单
        if(StringUtils.isNotBlank(deployId)) {
            SysForm sysForm = sysFormDeployService.selectByDeployId(deployId);
            if(Objects.isNull(sysForm)) {
                return R.fail("请先配置流程表单");
            }
            map.put("formData", JSONObject.parseObject(sysForm.getFormContent()));
        }
        return R.ok(map);
    }

    /**
     * 根据任务ID查询挂载的表单信息
     *
     * @param taskId 任务Id
     *
     * @return
     */
    @Override
    public Task getTaskForm(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        return task;
    }

    /**
     * 获取流程过程图
     *
     * @param processId
     *
     * @return
     */
    @Override
    public InputStream diagram(String processId) {
        String processDefinitionId;
        // 获取当前的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        // 如果流程已经结束，则得到结束节点
        if(Objects.isNull(processInstance)) {
            HistoricProcessInstance pi = historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();

            processDefinitionId = pi.getProcessDefinitionId();
        } else {// 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
            processDefinitionId = pi.getProcessDefinitionId();
        }

        // 获得活动的节点
        List<HistoricActivityInstance> highLightedFlowList = historyService.createHistoricActivityInstanceQuery()
                                                                           .processInstanceId(processId)
                                                                           .orderByHistoricActivityInstanceStartTime()
                                                                           .asc()
                                                                           .list();

        List<String> highLightedFlows = new ArrayList<>();
        List<String> highLightedNodes = new ArrayList<>();
        //高亮线
        for(HistoricActivityInstance tempActivity : highLightedFlowList) {
            if("sequenceFlow".equals(tempActivity.getActivityType())) {
                //高亮线
                highLightedFlows.add(tempActivity.getActivityId());
            } else {
                //高亮节点
                highLightedNodes.add(tempActivity.getActivityId());
            }
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration configuration = processEngine.getProcessEngineConfiguration();
        //获取自定义图片My器
        ProcessDiagramGenerator diagramGenerator = new MyProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel,
                                                          "png",
                                                          highLightedNodes,
                                                          highLightedFlows,
                                                          configuration.getActivityFontName(),
                                                          configuration.getLabelFontName(),
                                                          configuration.getAnnotationFontName(),
                                                          configuration.getClassLoader(),
                                                          1.0,
                                                          true);
        return in;

    }

    /**
     * 获取流程执行过程
     *
     * @param procInsId
     *
     * @return
     */
    @Override
    public R<?> getFlowViewer(String procInsId) {
        List<FlowViewerDTO> flowViewerList = new ArrayList<>();
        FlowViewerDTO flowViewerDto;
        // 获得活动的节点
        List<HistoricActivityInstance> hisActIns = historyService.createHistoricActivityInstanceQuery()
                                                                 .processInstanceId(procInsId)
                                                                 .orderByHistoricActivityInstanceStartTime()
                                                                 .asc().list();
        for(HistoricActivityInstance activityInstance : hisActIns) {
            if(!"sequenceFlow".equals(activityInstance.getActivityType())) {
                flowViewerDto = new FlowViewerDTO();
                flowViewerDto.setKey(activityInstance.getActivityId());
                flowViewerDto.setCompleted(!Objects.isNull(activityInstance.getEndTime()));
                flowViewerList.add(flowViewerDto);
            }
        }
        return R.ok(flowViewerList);
    }

    /**
     * 获取流程变量
     *
     * @param taskId
     *
     * @return
     */
    @Override
    public R<?> processVariables(String taskId) {
        // 流程变量
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                                                                  .includeProcessVariables()
                                                                  .finished()
                                                                  .taskId(taskId)
                                                                  .singleResult();
        if(Objects.nonNull(historicTaskInstance)) {
            return R.ok(historicTaskInstance.getProcessVariables());
        } else {
            Map<String, Object> variables = taskService.getVariables(taskId);
            return R.ok(variables);
        }
    }

    /**
     * 获取下一节点
     *
     * @param flowTaskVo 任务
     *
     * @return
     */
    @Override
    public R<?> getNextFlowNode(FlowTaskVo flowTaskVo) {
        Task task = taskService.createTaskQuery().taskId(flowTaskVo.getTaskId()).singleResult();
        FlowNextDTO flowNextDto = new FlowNextDTO();
        if(Objects.nonNull(task)) {
            List<UserTask> nextUserTask = FindNextNodeUtil.getNextUserTasks(repositoryService, task, new HashMap<>());
            if(CollectionUtil.isNotEmpty(nextUserTask)) {
                for(UserTask userTask : nextUserTask) {
                    MultiInstanceLoopCharacteristics multiInstance = userTask.getLoopCharacteristics();
                    // 会签节点
                    if(Objects.nonNull(multiInstance)) {
                        List<SysUser> list = InitUtils.getUserList();

                        flowNextDto.setVars(ProcessConstants.PROCESS_MULTI_INSTANCE_USER);
                        flowNextDto.setType(ProcessConstants.PROCESS_MULTI_INSTANCE);
                        flowNextDto.setUserList(list);
                    } else {

                        // 读取自定义节点属性 判断是否是否需要动态指定任务接收人员、组
                        String dataType = userTask.getAttributeValue(ProcessConstants.NAMESPACE, ProcessConstants.PROCESS_CUSTOM_DATA_TYPE);
                        String userType = userTask.getAttributeValue(ProcessConstants.NAMESPACE, ProcessConstants.PROCESS_CUSTOM_USER_TYPE);

                        if(ProcessConstants.DATA_TYPE.equals(dataType)) {
                            // 指定单个人员
                            if(ProcessConstants.USER_TYPE_ASSIGNEE.equals(userType)) {
                                List<SysUser> list = InitUtils.getUserList();

                                flowNextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
                                flowNextDto.setType(ProcessConstants.USER_TYPE_ASSIGNEE);
                                flowNextDto.setUserList(list);
                            }
                            // 候选人员(多个)
                            if(ProcessConstants.USER_TYPE_USERS.equals(userType)) {
                                List<SysUser> list = InitUtils.getUserList();

                                flowNextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
                                flowNextDto.setType(ProcessConstants.USER_TYPE_USERS);
                                flowNextDto.setUserList(list);
                            }
                            // 候选组
                            if(ProcessConstants.USER_TYPE_GROUPS.equals(userType)) {
                                List<SysRole> sysRoles = InitUtils.getRoleList();

                                flowNextDto.setVars(ProcessConstants.PROCESS_APPROVAL);
                                flowNextDto.setType(ProcessConstants.USER_TYPE_GROUPS);
                                flowNextDto.setRoleList(sysRoles);
                            }
                        }
                    }
                }
            } else {
                return R.ok("流程已完结", null);
            }
        }
        return R.ok(flowNextDto);
    }

    /**
     * 流程完成时间处理
     *
     * @param ms
     *
     * @return
     */
    private String getDate(long ms) {

        long day = ms / (24 * 60 * 60 * 1000);
        long hour = (ms / (60 * 60 * 1000) - day * 24);
        long minute = ((ms / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long second = (ms / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - minute * 60);

        if(day > 0) {
            return day + "天" + hour + "小时" + minute + "分钟";
        }
        if(hour > 0) {
            return hour + "小时" + minute + "分钟";
        }
        if(minute > 0) {
            return minute + "分钟";
        }
        if(second > 0) {
            return second + "秒";
        } else {
            return 0 + "秒";
        }
    }

}