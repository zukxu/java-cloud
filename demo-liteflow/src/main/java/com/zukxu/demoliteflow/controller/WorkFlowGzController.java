package com.zukxu.demoliteflow.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zukxu.demoliteflow.service.WorkFlowGZService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 对接大音平台工单接口
 * 适合申请工单
 * 由省公司发起申请，工单在大音平台进行流转
 * </p>
 *
 * @author xupu
 * @since 2021-11-18 14:12
 */
@RestController
@RequestMapping("/sync/gz")
@AllArgsConstructor
public class WorkFlowGzController {

    final private WorkFlowGZService workFlowGZService;

    /**
     * 省分派发申请单
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/DispatchCSS")
    public R<?> DispatchCSS(@RequestBody Map<String, Object> paramMap) {
        return workFlowGZService.dispatchCSS(paramMap);
    }

    @LogAnnotation(module = "sync/gz")
    @PostMapping("/addDocument")
    public R<WorkFlowDocument> addDocument(@RequestBody WorkFlowDocument document) {
        return workFlowGZService.addDocument(document);
    }

    /**
     * 工单回复
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/ReplyCSS")
    public R<?> ReplyCSS(@RequestBody Map<String, Object> paramMap) {
        ReplyCSSDto replyCSSDto = JSON.parseObject(JSON.toJSONString(paramMap), ReplyCSSDto.class);
        return workFlowGZService.replyCSS(replyCSSDto);
    }

    /**
     * 工单查询
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/QueryCSS")
    public R<?> QueryCSS(@RequestBody QueryCSSDto queryCSS) {
        return workFlowGZService.queryCSS(queryCSS);
    }

    /**
     * 工单归档
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/StatementCSS")
    public R<?> StatementCSS(@RequestBody StatementCSSDto statementCSS) {
        return workFlowGZService.statementCSS(statementCSS);
    }

    /**
     * 工单撤单
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/WithdrawCSS")
    public R<?> WithdrawCSS(@RequestBody WithdrawCSSDto withdrawCSS) {
        return workFlowGZService.withdrawCSS(withdrawCSS);
    }

    /**
     * 工单再处理
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/ReprocessCSS")
    public R<?> ReprocessCSS(@RequestBody ReprocessCSSDto reprocessCSSDto) {
        return workFlowGZService.reprocessCSS(reprocessCSSDto);
    }

    /**
     * 工单催办
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/UrgeCSS")
    public R<?> UrgeCSS(@RequestBody UrgeCSSDto urgeCSS) {
        return workFlowGZService.urgeCSS(urgeCSS);
    }

    /**
     * 附件上传
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/upload")
    public R<?> upload(MultipartFile file) throws Exception {
        String fName = workFlowGZService.uploadToJT(file);
        return R.ok(fName);
    }

    /**
     * 重新刷新拉取来自集团的附件
     *
     * @return R
     */
    @GetMapping("/download")
    public R<?> downloadFile(String path, @RequestParam(defaultValue = "2") String unit) throws Exception {
        //只拉取集团附件
        if(StrUtil.equals(CSVSConstant.UNIT_JT, unit)) {
            FileUtil.download(path);
        }
        return R.ok();
    }

    /**
     * 获取附件上传校验文件
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @GetMapping("/getUploadCheckFile")
    public R<?> getUploadCheckFile() {
        return workFlowGZService.getUploadCheckFile();
    }

    /**
     * <p>
     * 工单通用接口 由省公司发起调用集团接口
     * </p>
     *
     * @author xupu
     * @since 2022/3/31 11:22
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/CurrencyCSS")
    public R<?> CurrencyCSS(@RequestBody CurrentCSSDto currentCSS) {
        return workFlowGZService.currencyCSS(currentCSS);
    }

    /**
     * 数据同步 省内调用集团地址进行推送数据
     *
     * @param syncDataDto SyncDataDto
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/SyncData")
    public R<?> SyncData(@RequestBody SyncDataDto syncDataDto) {
        return workFlowGZService.syncData(syncDataDto);
    }

    /**
     * 测试工单同步删除接口
     *
     * @param workFlowF WorkFlowF
     *
     * @return R
     */
    @LogAnnotation(module = "sync/gz")
    @PostMapping("/TestjobCSS")
    public R<?> TestJobCSS(@RequestBody WorkFlowF workFlowF) {
        return workFlowGZService.TestJobCSS(workFlowF);
    }

}
