package com.zukxu.flowable.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zukxu.flowable.domain.entity.SysForm;
import com.zukxu.flowable.domain.entity.SysFormDeploy;

public interface SysFormDeployService extends IService<SysFormDeploy> {
    /**
     * 查询流程挂着的表单
     *
     * @param deployId
     * @return
     */
    SysForm selectByDeployId(String deployId);

    /**
     * 根据deployId删除
     * @param deployId
     * @return
     */
    void deleteByDeployId(String deployId);
}
