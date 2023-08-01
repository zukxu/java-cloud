package com.zukxu.flowable.controller;

import com.zukxu.common.result.R;
import com.zukxu.flowable.model.vo.FlowTaskVo;
import com.zukxu.flowable.service.FlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 作流流程实例管理
 * </p>
 *
 * @author xupu
 * @since 2021/12/16 17:14
 */
@Slf4j
@Api(tags = "工作流流程实例管理")
@RestController
@RequestMapping("/flowable/instance")
public class FlowInstanceController {

    @Autowired
    private FlowInstanceService flowInstanceService;

    @ApiOperation(value = "根据流程定义id启动流程实例")
    @PostMapping("/startBy/{procDefId}")
    public R<?> startById(@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
                          @ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables
                         ) {
        return flowInstanceService.startProcessInstanceById(procDefId, variables);

    }

    @ApiOperation(value = "激活或挂起流程实例")
    @PostMapping(value = "/updateState")
    public R<?> updateState(@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
                            @ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId
                           ) {
        flowInstanceService.updateState(state, instanceId);
        return R.ok();
    }

    @ApiOperation("结束流程实例")
    @PostMapping(value = "/stopProcessInstance")
    public R<?> stopProcessInstance(@RequestBody FlowTaskVo flowTaskVo) {
        flowInstanceService.stopProcessInstance(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "删除流程实例")
    @DeleteMapping(value = "/delete")
    public R<?> delete(@ApiParam(value = "流程实例ID", required = true) @RequestParam String[] ids) {
        for (String id : ids) {
            flowInstanceService.delete(id);
        }
        return R.ok();
    }
}