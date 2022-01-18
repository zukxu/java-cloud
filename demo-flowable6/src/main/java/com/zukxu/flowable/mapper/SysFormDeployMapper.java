package com.zukxu.flowable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zukxu.flowable.domain.entity.SysForm;
import com.zukxu.flowable.domain.entity.SysFormDeploy;

public interface SysFormDeployMapper extends BaseMapper<SysFormDeploy> {
    /**
     * 查询流程挂着的表单
     *
     * @param deployId
     *
     * @return
     */
    SysForm selectByDeployId(String deployId);

    /**
     * 删除挂载的表单
     * @param deployId
     */
    void deleteByDeployId(String deployId);
}
