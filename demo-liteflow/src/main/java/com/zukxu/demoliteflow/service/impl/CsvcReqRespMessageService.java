package com.zukxu.demoliteflow.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.zukxu.demoliteflow.mapper.CsvcReqRespMessageMapper;
import com.zukxu.demoliteflow.model.csvc.CsvcReqRespMessage;
/**
 * <p>
 *  ${END}
 * </p>
 *
 * @author xupu
 * @since 2022-09-25 00:02:53
 */
@Service
public class CsvcReqRespMessageService{

    @Resource
    private CsvcReqRespMessageMapper csvcReqRespMessageMapper;

    
    public int deleteByPrimaryKey(String transido) {
        return csvcReqRespMessageMapper.deleteByPrimaryKey(transido);
    }

    
    public int insert(CsvcReqRespMessage record) {
        return csvcReqRespMessageMapper.insert(record);
    }

    
    public int insertSelective(CsvcReqRespMessage record) {
        return csvcReqRespMessageMapper.insertSelective(record);
    }

    
    public CsvcReqRespMessage selectByPrimaryKey(String transido) {
        return csvcReqRespMessageMapper.selectByPrimaryKey(transido);
    }

    
    public int updateByPrimaryKeySelective(CsvcReqRespMessage record) {
        return csvcReqRespMessageMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(CsvcReqRespMessage record) {
        return csvcReqRespMessageMapper.updateByPrimaryKey(record);
    }

}
