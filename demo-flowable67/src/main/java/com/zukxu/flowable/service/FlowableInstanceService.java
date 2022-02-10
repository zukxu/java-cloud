package com.zukxu.flowable.service;

import com.zukxu.common.result.R;
import com.zukxu.flowable.FlowServiceFactory;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId, variables);

        return R.ok(processInstance);
    }

    /**
     * 根据流程定义id启动流程实例
     *
     * @param procDefKey 流程定义Key
     * @param variables  流程变量
     * @return R
     */
    public R<?> addNewInstanceByKey(String procDefKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(procDefKey, variables);

        return R.ok(processInstance);
    }
}
