package com.zukxu.flowable.controller;

import com.zukxu.common.result.R;
import com.zukxu.flowable.model.dto.FlowTaskDTO;
import com.zukxu.flowable.model.vo.FlowTaskVo;
import com.zukxu.flowable.service.FlowTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * 工作流任务管理
 * </p>
 *
 * @author xupu
 * @since 2021/12/16 17:15
 */
@Slf4j
@Api(tags = "工作流流程任务管理")
@RestController
@RequestMapping("/flowable/task")
public class FlowTaskController {

    @Autowired
    private FlowTaskService flowTaskService;

    @ApiOperation(value = "我发起的流程", response = FlowTaskDTO.class)
    @GetMapping(value = "/myProcess")
    public R<?> myProcess(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
                          @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize
                         ) {
        return flowTaskService.myProcess(pageNum, pageSize);
    }

    @ApiOperation(value = "取消申请", response = FlowTaskDTO.class)
    @PostMapping(value = "/stopProcess")
    public R<?> stopProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.stopProcess(flowTaskVo);
    }

    @ApiOperation(value = "撤回流程", response = FlowTaskDTO.class)
    @PostMapping(value = "/revokeProcess")
    public R<?> revokeProcess(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.revokeProcess(flowTaskVo);
    }

    @ApiOperation(value = "获取待办列表", response = FlowTaskDTO.class)
    @GetMapping(value = "/todoList")
    public R<?> todoList(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
                         @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize
                        ) {
        return flowTaskService.todoList(pageNum, pageSize);
    }

    @ApiOperation(value = "获取已办任务", response = FlowTaskDTO.class)
    @GetMapping(value = "/finishedList")
    public R<?> finishedList(@ApiParam(value = "当前页码", required = true) @RequestParam Integer pageNum,
                             @ApiParam(value = "每页条数", required = true) @RequestParam Integer pageSize
                            ) {
        return flowTaskService.finishedList(pageNum, pageSize);
    }

    @ApiOperation(value = "流程历史流转记录", response = FlowTaskDTO.class)
    @GetMapping(value = "/flowRecord")
    public R<?> flowRecord(String procInsId, String deployId) {
        return flowTaskService.flowRecord(procInsId, deployId);
    }

    @ApiOperation(value = "获取流程变量", response = FlowTaskDTO.class)
    @GetMapping(value = "/processVariables/{taskId}")
    public R<?> processVariables(@ApiParam(value = "流程任务Id") @PathVariable(value = "taskId") String taskId) {
        return flowTaskService.processVariables(taskId);
    }

    @ApiOperation(value = "审批任务")
    @PostMapping(value = "/complete")
    public R<?> complete(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.complete(flowTaskVo);
    }

    @ApiOperation(value = "驳回任务")
    @PostMapping(value = "/reject")
    public R<?> taskReject(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReject(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "退回任务")
    @PostMapping(value = "/return")
    public R<?> taskReturn(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.taskReturn(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "获取所有可回退的节点")
    @PostMapping(value = "/returnList")
    public R<?> findReturnTaskList(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.findReturnTaskList(flowTaskVo);
    }

    @ApiOperation(value = "删除任务")
    @DeleteMapping(value = "/delete")
    public R<?> delete(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.deleteTask(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "认领/签收任务")
    @PostMapping(value = "/claim")
    public R<?> claim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.claim(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "取消认领/签收任务")
    @PostMapping(value = "/unClaim")
    public R<?> unClaim(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.unClaim(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "委派任务")
    @PostMapping(value = "/delegate")
    public R<?> delegate(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.delegateTask(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "转办任务")
    @PostMapping(value = "/assign")
    public R<?> assign(@RequestBody FlowTaskVo flowTaskVo) {
        flowTaskService.assignTask(flowTaskVo);
        return R.ok();
    }

    @ApiOperation(value = "获取下一节点")
    @PostMapping(value = "/nextFlowNode")
    public R<?> getNextFlowNode(@RequestBody FlowTaskVo flowTaskVo) {
        return flowTaskService.getNextFlowNode(flowTaskVo);
    }

    /**
     * 生成流程图
     *
     * @param processId 任务ID
     */
    @RequestMapping("/diagram/{processId}")
    public void genProcessDiagram(HttpServletResponse response, @PathVariable("processId") String processId) {
        InputStream inputStream = flowTaskService.diagram(processId);
        OutputStream os = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成流程图
     *
     * @param procInsId 任务ID
     */
    @RequestMapping("/flowViewer/{procInsId}")
    public R<?> getFlowViewer(@PathVariable("procInsId") String procInsId) {
        return flowTaskService.getFlowViewer(procInsId);
    }
}
