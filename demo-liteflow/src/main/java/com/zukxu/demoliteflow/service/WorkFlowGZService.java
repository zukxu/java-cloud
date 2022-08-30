package com.zukxu.demoliteflow.service;

import com.bonc.common.response.R;
import com.bonc.flowable.model.condition.csvs.*;
import com.bonc.flowable.model.csvc.WorkFlowDocument;
import com.bonc.flowable.model.csvc.WorkFlowF;
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
     *
     * @return R
     */
    void  dispatchByWorkFlowId(String identifier);

    /**
     * 派单
     * 建单就进行派单
     *
     * @param param param
     *
     * @return R
     */
    void  dispatchCSS(Map<String, Object> param);

    /**
     * 挂载档案
     *
     * @param document document
     *
     * @return R
     */
    R<WorkFlowDocument> addDocument(WorkFlowDocument document);

    /**
     * 回复
     *
     * @param replyCSS
     *
     * @return
     */
    void  replyCSS(ReplyCSSDto replyCSS);

    /**
     * 申请单上传附件
     *
     * @param file
     *
     * @return
     */
    String uploadToJT(MultipartFile file);

    /**
     * 回复集团上传附件
     *
     * @param file
     */
    void uploadToJT(String file);

    /**
     * 查询
     *
     * @param queryCSS
     *
     * @return
     */
    void  queryCSS(QueryCSSDto queryCSS);

    /**
     * 归档
     *
     * @param statementCSS
     *
     * @return
     */
    void  statementCSS(StatementCSSDto statementCSS);

    /**
     * 撤单
     *
     * @param withdrawCSS
     *
     * @return
     */
    void  withdrawCSS(WithdrawCSSDto withdrawCSS);

    /**
     * 再处理
     *
     * @param reprocessCSS
     *
     * @return
     */
    void  reprocessCSS(ReprocessCSSDto reprocessCSS);

    /**
     * 催办
     *
     * @param urgeCSS
     *
     * @return
     */
    void  urgeCSS(UrgeCSSDto urgeCSS);

    /**
     * 工单通用接口
     * Refund 退单转派
     * HandlingComment 中途意见
     *
     * @param currentCSS
     *
     * @return
     */
    void  currencyCSS(CurrentCSSDto currentCSS);

    /**
     * 工单信息同步
     * 对于省专内部流转和跨平台工单，需要省专公司将工单详情和处理过程数据上传至集团大音平台
     *
     * @author xupu
     * @since 2022/3/31 11:28
     */
    void  syncData(SyncDataDto syncData);

    /**
     * 获取上传校验文件
     *
     * @return R
     */
    void  getUploadCheckFile();

    /**
     * 测试工单同步删除
     *
     * @param workFlowF WorkFlowF
     *
     * @return R
     */
    void  TestJobCSS(WorkFlowF workFlowF);

}
