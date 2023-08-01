package com.zukxu.demoliteflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.zukxu.demoliteflow.constant.Chain;
import com.zukxu.demoliteflow.context.WorkFlowContext;
import com.zukxu.demoliteflow.model.WorkFlowF;
import com.zukxu.demoliteflow.model.common.*;
import com.zukxu.demoliteflow.service.WorkFlowGZService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * GZCSVCServiceImpl
 * </p>
 *
 * @author xupu
 * @since 2022-03-31 11:47
 */
@Slf4j
@Service
public class WorkFlowGZServiceImpl implements WorkFlowGZService {

    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public void dispatchByWFId(String identifier) {

    }

    @Override
    public void dispatchCSS(Map<String, Object> param) {
        LiteflowResponse resp = flowExecutor.execute2Resp(Chain.SN_DIS, param, WorkFlowContext.class);
        if (resp.isSuccess()) {
            log.info("SUCCESS");
        } else {
            log.error("ERROR");
        }
    }

    @Override
    public void replyCSS(CReply cReply) {

    }

    @Override
    public void queryCSS(CQuery cQuery) {

    }

    @Override
    public void statementCSS(CStatement cStatement) {

    }

    @Override
    public void withdrawCSS(CWithdraw cWithdraw) {

    }

    @Override
    public void reprocessCSS(CReprocess cReprocess) {

    }

    @Override
    public void urgeCSS(CUrge cUrge) {

    }

    @Override
    public void currencyCSS(CCurrency cCurrency) {

    }

    @Override
    public void syncData(CSyncData cSyncData) {

    }

    @Override
    public void TestJobCSS(WorkFlowF workFlowF) {

    }

    @Override
    public String uploadToJT(MultipartFile file) {
        return null;
    }

    @Override
    public void uploadToJT(String file) {

    }

    @Override
    public void getUploadCheckFile() {

    }

}

