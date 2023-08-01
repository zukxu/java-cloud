package com.zukxu.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.flowable.model.entity.SysForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程表单Mapper接口
 *
 * @author zukxu
 * @date 2021-03-30
 */
public interface SysFormMapper extends BaseMapper<SysForm> {

    /**
     * 查询流程表单
     *
     * @param formId 流程表单ID
     * @return 流程表单
     */
    SysForm selectSysFormById(String formId);

    /**
     * 查询流程表单列表
     *
     * @param formName 流程表单
     * @return 流程表单集合
     */
    List<SysForm> selectSysFormList(String formName);

    /**
     * 删除流程表单
     *
     * @param formId 流程表单ID
     * @return 结果
     */
    int deleteSysFormById(String formId);

    /**
     * 批量删除流程表单
     *
     * @param formIds 需要删除的数据ID
     * @return 结果
     */
    int deleteSysFormByIds(@Param("formIds") String[] formIds);

    /**
     * 表单是否绑定任务
     *
     * @param formId
     * @return
     */
    int isBindTask(String formId);
}
