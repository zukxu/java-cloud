package com.zukxu.flowable.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.flowable.model.entity.SysForm;

import java.util.List;

public interface SysFormService extends IService<SysForm> {
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
     * @param formName 流程表单名
     * @return 流程表单集合
     */
    List<SysForm> selectSysFormList(String formName);

    /**
     * 查询是否存在绑定
     *
     * @param formIds 需要删除的流程表单ID
     * @return 结果
     */
    boolean isBindTask(String[] formIds);
}
