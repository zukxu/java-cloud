package com.zukxu.flowable.controller;

import com.zukxu.common.result.R;
import com.zukxu.flowable.service.FlowableInstanceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 流程实例controller
 * </p>
 *
 * @author xupu
 * @since 2022-02-10 15:35
 */
@RestController
@RequestMapping("/flowable/instance")
public class FlowableInstanceController {
    @Autowired
    private FlowableInstanceService flowableInstanceService;

    /**
     * 根据流程定义id启动流程实例
     *
     * @param procDefId 流程定义id
     * @param variables 流程变量
     * @return
     */
    @PostMapping("/startByProcDefId/{procDefId}")
    public R<?> addNewInstanceById(@PathVariable("procDefId") String procDefId, @RequestBody Map<String, Object> variables) {
        return flowableInstanceService.addNewInstanceById(procDefId, variables);
    }

    /**
     * 根据流程定义key启动流程实例
     *
     * @param procDefKey 流程定义key
     * @param variables  流程变量
     * @return
     */
    @PostMapping("/startByProcDefKey/{procDefKey}")
    public R<?> addNewInstanceByKey(@PathVariable("procDefKey") String procDefKey, @RequestBody Map<String, Object> variables) {
        return flowableInstanceService.addNewInstanceByKey(procDefKey, variables);
    }

    @ApiOperation(value = "激活或挂起流程实例")
    @PostMapping(value = "/updateState")
    public R<?> updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                            @ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId) {
        return flowableInstanceService.updateState(state, instanceId);
    }

    @ApiOperation(value = "删除流程实例")
    @DeleteMapping(value = "/delete")
    public R<?> delete(@ApiParam(value = "流程实例ID", required = true) @RequestParam String[] ids) {
        return flowableInstanceService.deleteByIds(ids);
    }

}
