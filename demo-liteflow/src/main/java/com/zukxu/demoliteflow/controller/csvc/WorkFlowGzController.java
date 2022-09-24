package com.zukxu.demoliteflow.controller.csvc;

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
     */

    @PostMapping("/DispatchCSS")
    public void DispatchCSS(@RequestBody Map<String, Object> paramMap) {
        workFlowGZService.dispatchCSS(paramMap);
    }


    /**
     * 工单回复
     *
     * @ R
     */

    @PostMapping("/ReplyCSS")
    public void ReplyCSS(@RequestBody Map<String, Object> paramMap) {
        ReplyCSSDto replyCSSDto = JSON.parseObject(JSON.toJSONString(paramMap), ReplyCSSDto.class);
        workFlowGZService.replyCSS(replyCSSDto);
    }

    /**
     * 工单查询
     *
     * @ R
     */

    @PostMapping("/QueryCSS")
    public void QueryCSS(@RequestBody QueryCSSDto queryCSS) {
        workFlowGZService.queryCSS(queryCSS);
    }

    /**
     * 工单归档
     *
     * @ R
     */

    @PostMapping("/StatementCSS")
    public void StatementCSS(@RequestBody StatementCSSDto statementCSS) {
        workFlowGZService.statementCSS(statementCSS);
    }

    /**
     * 工单撤单
     *
     * @ R
     */

    @PostMapping("/WithdrawCSS")
    public void WithdrawCSS(@RequestBody WithdrawCSSDto withdrawCSS) {
        workFlowGZService.withdrawCSS(withdrawCSS);
    }

    /**
     * 工单再处理
     *
     * @ R
     */

    @PostMapping("/ReprocessCSS")
    public void ReprocessCSS(@RequestBody ReprocessCSSDto reprocessCSSDto) {
        workFlowGZService.reprocessCSS(reprocessCSSDto);
    }

    /**
     * 工单催办
     *
     * @ R
     */

    @PostMapping("/UrgeCSS")
    public void UrgeCSS(@RequestBody UrgeCSSDto urgeCSS) {
        workFlowGZService.urgeCSS(urgeCSS);
    }

    /**
     * 附件上传
     *
     * @ R
     */

    @PostMapping("/upload")
    public void upload(MultipartFile file) throws Exception {
        String fName = workFlowGZService.uploadToJT(file);
        R.ok(fName);
    }

    /**
     * 重新刷新拉取来自集团的附件
     *
     * @ R
     */
    @GetMapping("/download")
    public void downloadFile(String path, @RequestParam(defaultValue = "2") String unit) throws Exception {
        //只拉取集团附件
        if(StrUtil.equals(CSVSConstant.UNIT_JT, unit)) {
            FileUtil.download(path);
        }
        R.ok();
    }

    /**
     * 获取附件上传校验文件
     *
     * @ R
     */

    @GetMapping("/getUploadCheckFile")
    public void getUploadCheckFile() {
        workFlowGZService.getUploadCheckFile();
    }

    /**
     * <p>
     * 工单通用接口 由省公司发起调用集团接口
     * </p>
     *
     * @author xupu
     * @since 2022/3/31 11:22
     */

    @PostMapping("/CurrencyCSS")
    public void CurrencyCSS(@RequestBody CurrentCSSDto currentCSS) {
        workFlowGZService.currencyCSS(currentCSS);
    }

    /**
     * 数据同步 省内调用集团地址进行推送数据
     *
     * @param syncDataDto SyncDataDto
     *
     * @ R
     */

    @PostMapping("/SyncData")
    public void SyncData(@RequestBody SyncDataDto syncDataDto) {
        workFlowGZService.syncData(syncDataDto);
    }

    /**
     * 测试工单同步删除接口
     *
     * @param workFlowF WorkFlowF
     *
     * @ R
     */

    @PostMapping("/TestjobCSS")
    public void TestJobCSS(@RequestBody WorkFlowF workFlowF) {
        workFlowGZService.TestJobCSS(workFlowF);
    }

}
