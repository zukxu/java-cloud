package com.zukxu.demoliteflow.service;

import com.zukxu.demoliteflow.model.WorkFlowF;
import com.zukxu.demoliteflow.model.common.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * WorkFlowGZService
 * </p>
 *
 * @author xupu
 * @since 2022-03-31 11:47
 */
public interface WorkFlowGZService {

    /**
     * 通过工单id进行派发
     * 建单 初审完再派单 不需要再次启动流程
     *
     * @param identifier identifier
     */
    void dispatchByWFId(String identifier);

    /**
     * 派单
     * 建单就进行派单
     *
     * @param param map
     */
    void dispatchCSS(Map<String, Object> param);

    /**
     * 挂载档案
     *
     * @param document document
     *
     * @return R
     */
    //R<WorkFlowDocument> addDocument(WorkFlowDocument document);

    /**
     * 回复
     *
     * @param cReply CReply
     */
    void replyCSS(CReply cReply);

    /**
     * 查询
     *
     * @param cQuery CQuery
     */
    void queryCSS(CQuery cQuery);

    /**
     * 归档
     *
     * @param cStatement CStatement
     */
    void statementCSS(CStatement cStatement);

    /**
     * 撤单
     *
     * @param cWithdraw CWithdraw
     */
    void withdrawCSS(CWithdraw cWithdraw);

    /**
     * 再处理
     *
     * @param cReprocess CReprocess
     */
    void reprocessCSS(CReprocess cReprocess);

    /**
     * 催办
     *
     * @param cUrge CUrge
     */
    void urgeCSS(CUrge cUrge);

    /**
     * 工单通用接口
     * Refund 退单转派
     * HandlingComment 中途意见
     *
     * @param cCurrency CCurrency
     */
    void currencyCSS(CCurrency cCurrency);

    /**
     * 工单信息同步
     * 对于省专内部流转和跨平台工单，需要省专公司将工单详情和处理过程数据上传至集团大音平台
     *
     * @author xupu
     * @since 2022/3/31 11:28
     */
    void syncData(CSyncData cSyncData);

    /**
     * 测试工单同步删除
     *
     * @param workFlowF WorkFlowF
     */
    void TestJobCSS(WorkFlowF workFlowF);

    /**
     * 申请单上传附件
     *
     * @param file MultipartFile
     *
     * @return fileName
     */
    String uploadToJT(MultipartFile file);

    /**
     * 回复集团上传附件
     *
     * @param file fileName
     */
    void uploadToJT(String file);

    /**
     * 获取上传校验文件
     */
    void getUploadCheckFile();


}
