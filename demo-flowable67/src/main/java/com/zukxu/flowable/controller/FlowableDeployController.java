package com.zukxu.flowable.controller;

import com.zukxu.common.result.R;
import com.zukxu.common.utils.ServletUtils;
import com.zukxu.flowable.service.FlowableDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 流程部署controller
 * </p>
 *
 * @author xupu
 * @since 2022-02-10 15:19
 */
@RestController
@RequestMapping("/flowable/definition")
public class FlowableDeployController {
    @Autowired
    private FlowableDefinitionService flowableDefinitionService;

    /**
     * 新增流程定义
     *
     * @return
     */
    @PostMapping
    public R<?> addDefinition() {
        return R.ok();
    }

    /**
     * 部署流程定义
     *
     * @return
     */
    @PostMapping("/deploy")
    public R<?> deployDefinition() {
        return flowableDefinitionService.deployDefinition();
    }

    @GetMapping("/list")
    public R<?> listDeploy() {
        Integer pageNum = ServletUtils.getParameterToInt("pageNum");
        Integer pageSize = ServletUtils.getParameterToInt("pageSize");
        return flowableDefinitionService.listDeploy(pageNum, pageSize);
    }

    /**
     * 查询是否部署
     *
     * @param deployId 部署id
     * @return
     */
    @GetMapping("/getDeploy/{deployId}")
    public R<?> getDeployDetails(@PathVariable("deployId") String deployId) {
        return flowableDefinitionService.getDeployDetails(deployId);
    }

}
