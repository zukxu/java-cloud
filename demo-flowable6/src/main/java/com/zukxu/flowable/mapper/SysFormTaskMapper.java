package com.zukxu.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.flowable.model.entity.SysFormTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程任务关联单Mapper接口
 *
 * @author zukxu
 * @date 2021-04-03
 */
public interface SysFormTaskMapper extends BaseMapper<SysFormTask> {

    /**
     * 查询流程任务关联单
     *
     * @param id 流程任务关联单ID
     * @return 流程任务关联单
     */
    SysFormTask selectSysFormTaskById(String id);

    /**
     * 查询流程任务关联单列表
     *
     * @param sysTaskForm 流程任务关联单
     * @return 流程任务关联单集合
     */
    List<SysFormTask> selectSysFormTaskList(SysFormTask sysTaskForm);

    /**
     * 新增流程任务关联单
     *
     * @param sysTaskForm 流程任务关联单
     * @return 结果
     */
    int insertSysFormTask(SysFormTask sysTaskForm);

    /**
     * 修改流程任务关联单
     *
     * @param sysTaskForm 流程任务关联单
     * @return 结果
     */
    int updateSysFormTask(SysFormTask sysTaskForm);

    /**
     * 删除流程任务关联单
     *
     * @param id 流程任务关联单ID
     * @return 结果
     */
    int deleteSysFormTaskById(String id);

    /**
     * 批量删除流程任务关联单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteSysFormTaskByIds(@Param("ids") String[] ids);

}
