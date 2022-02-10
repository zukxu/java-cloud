package com.zukxu.flowable.controller;

import com.zukxu.common.result.R;
import com.zukxu.flowable.service.FlowableDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 流程部署controller
 * </p>
 *
 * @author xupu
 * @since 2022-02-10 15:19
 */
@RestController
@RequestMapping("flowable/definition")
public class FlowableDeployController {
    @Autowired
    private FlowableDefinitionService flowableDefinitionService;

    /**
     * 新增流程定义
     * @return
     */
    @PostMapping
    public R<?> addDefinition() {
        return R.ok();
    }
  /**
     * 部署流程定义
     * @return
     */
    @PostMapping("/deploy")
    public R<?> deployDefinition() {
        flowableDefinitionService.deployDefinition();
        return R.ok();
    }

}
