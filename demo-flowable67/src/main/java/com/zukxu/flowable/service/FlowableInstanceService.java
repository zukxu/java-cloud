package com.zukxu.flowable.service;

import com.zukxu.common.result.R;
import com.zukxu.common.result.RMap;
import com.zukxu.flowable.FlowServiceFactory;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 流程定义
 * </p>
 *
 * @author xupu
 * @since 2022-02-10 15:12
 */
@Service
public class FlowableInstanceService extends FlowServiceFactory {
    /**
     * 根据流程定义id启动流程实例
     *
     * @param procDefId 流程定义id
     * @param variables 流程变量
     * @return R
     */
    public R<?> addNewInstanceById(String procDefId, Map<String, Object> variables) {
        //variables.put("employee", employee);
        //variables.put("nrOfHolidays", nrOfHolidays);
        //variables.put("description", description);
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId, variables);
            RMap rMap = new RMap().put("processInstanceId", processInstance.getProcessInstanceId());
            return R.ok(rMap).message("流程启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("流程启动错误");
        }
    }

    /**
     * 根据流程定义id启动流程实例
     *
     * @param procDefKey 流程定义Key
     * @param variables  流程变量
     * @return R
     */
    public R<?> addNewInstanceByKey(String procDefKey, Map<String, Object> variables) {
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(procDefKey, variables);
            RMap rMap = new RMap().put("processInstanceId", processInstance.getProcessInstanceId());
            return R.ok(rMap).message("流程启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail("流程启动错误");
        }
    }

    /**
     * 激活/挂起流程实例
     *
     * @param state      状态
     * @param instanceId 流程实例id
     * @return
     */
    public R<?> updateState(Integer state, String instanceId) {
        // 激活
        if (state == 1) {
            runtimeService.activateProcessInstanceById(instanceId);
            return R.ok().message("流程实例激活成功");
        }
        // 挂起
        if (state == 2) {
            runtimeService.suspendProcessInstanceById(instanceId);
            return R.ok().message("流程实例挂起成功");
        }
        return R.fail();
    }

    public R<?> deleteByIds(String[] ids) {
        for (String instanceId : ids) {
            // 查询历史数据
            HistoricProcessInstance historicProcessInstance = getHistoricProcessInstanceById(instanceId);
            if (historicProcessInstance.getEndTime() != null) {
                historyService.deleteHistoricProcessInstance(historicProcessInstance.getId());
                return R.fail(instanceId + "实例未结束,删除失败！");
            }
            // 删除流程实例
            runtimeService.deleteProcessInstance(instanceId, "删除实例");
            // 删除历史流程实例
            historyService.deleteHistoricProcessInstance(instanceId);
        }
        return R.ok().message("删除成功");
    }

    /**
     * 根据流程id查询历史记录
     *
     * @param processInstanceId
     * @return
     */
    private HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance)) {
            throw new FlowableObjectNotFoundException("流程实例不存在: " + processInstanceId);
        }
        return historicProcessInstance;
    }
}
