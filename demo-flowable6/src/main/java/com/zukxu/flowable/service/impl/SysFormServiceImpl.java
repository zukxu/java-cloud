package com.zukxu.flowable.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zukxu.flowable.domain.entity.SysForm;
import com.zukxu.flowable.mapper.SysFormMapper;
import com.zukxu.flowable.service.SysFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysFormServiceImpl extends ServiceImpl<SysFormMapper, SysForm> implements SysFormService {


    @Autowired
    private SysFormMapper sysFormMapper;

    /**
     * 查询流程表单
     *
     * @param formId 流程表单ID
     * @return 流程表单
     */
    @Override
    public SysForm selectSysFormById(String formId) {
        return sysFormMapper.selectSysFormById(formId);
    }

    /**
     * 查询流程表单列表
     *
     * @param formName 流程表单
     * @return 流程表单
     */
    @Override
    public List<SysForm> selectSysFormList(String formName) {
        return sysFormMapper.selectSysFormList(formName);
    }



    @Override
    public boolean isBindTask(String[] formIds) {
        for (String formId : formIds) {
            if (sysFormMapper.isBindTask(formId) > 0) {
                return true;
            }
        }
        return false;
    }

}
