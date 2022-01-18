package com.zukxu.flowable.service;


import com.zukxu.flowable.domain.entity.SysFormTask;

import java.util.List;


public interface SysFormTaskService {
    /**
     * 查询流程任务关联单
     *
     * @param id 流程任务关联单ID
     * @return 流程任务关联单
     */
    SysFormTask selectSysTaskFormById(Long id);

    /**
     * 查询流程任务关联单列表
     *
     * @param sysTaskForm 流程任务关联单
     * @return 流程任务关联单集合
     */
    List<SysFormTask> selectSysTaskFormList(SysFormTask sysTaskForm);

    /**
     * 新增流程任务关联单
     *
     * @param sysTaskForm 流程任务关联单
     * @return 结果
     */
    int insertSysTaskForm(SysFormTask sysTaskForm);

    /**
     * 修改流程任务关联单
     *
     * @param sysTaskForm 流程任务关联单
     * @return 结果
     */
    int updateSysTaskForm(SysFormTask sysTaskForm);

    /**
     * 批量删除流程任务关联单
     *
     * @param ids 需要删除的流程任务关联单ID
     * @return 结果
     */
    int deleteSysTaskFormByIds(Long[] ids);

    /**
     * 删除流程任务关联单信息
     *
     * @param id 流程任务关联单ID
     * @return 结果
     */
    int deleteSysTaskFormById(Long id);
}
