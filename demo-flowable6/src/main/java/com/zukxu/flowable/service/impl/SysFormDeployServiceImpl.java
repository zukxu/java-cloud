package com.zukxu.flowable.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.flowable.model.entity.SysForm;
import com.zukxu.flowable.model.entity.SysFormDeploy;
import com.zukxu.flowable.mapper.SysFormDeployMapper;
import com.zukxu.flowable.service.SysFormDeployService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysFormDeployServiceImpl extends ServiceImpl<SysFormDeployMapper, SysFormDeploy> implements SysFormDeployService {
    @Autowired
    private SysFormDeployMapper sysDeployFormMapper;

    /**
     * 查询流程挂着的表单
     *
     * @param deployId
     * @return
     */
    @Override
    public SysForm selectByDeployId(String deployId) {
        return sysDeployFormMapper.selectByDeployId(deployId);
    }

    @Override
    public void deleteByDeployId(String deployId) {
        sysDeployFormMapper.deleteByDeployId(deployId);
    }
}
