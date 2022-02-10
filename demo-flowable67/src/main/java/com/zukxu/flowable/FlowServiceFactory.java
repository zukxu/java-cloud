package com.zukxu.flowable;

import lombok.Getter;
import org.flowable.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * flowable 引擎注入封装
 * </p>
 *
 * @author xupu
 * @since 2021/12/16 10:51
 */
@Getter
@Component
public class FlowServiceFactory {

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected IdentityService identityService;

    @Autowired
    protected TaskService taskService;

    @Autowired
    protected FormService formService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected ManagementService managementService;


}
