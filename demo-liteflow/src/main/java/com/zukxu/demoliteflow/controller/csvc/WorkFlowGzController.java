package com.zukxu.demoliteflow.controller.csvc;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.zukxu.common.result.R;
import com.zukxu.demoliteflow.constant.CSVC;
import com.zukxu.demoliteflow.model.WorkFlowF;
import com.zukxu.demoliteflow.model.common.*;
import com.zukxu.demoliteflow.service.WorkFlowGZService;
import com.zukxu.demoliteflow.utils.FileUtil;
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
     */

    @PostMapping("/ReplyCSS")
    public void ReplyCSS(@RequestBody Map<String, Object> paramMap) {
        workFlowGZService.replyCSS(JSON.parseObject(JSON.toJSONString(paramMap), CReply.class));
    }

    /**
     * 工单查询
     */

    @PostMapping("/QueryCSS")
    public void QueryCSS(@RequestBody CQuery cQuery) {
        workFlowGZService.queryCSS(cQuery);
    }

    /**
     * 工单归档
     */

    @PostMapping("/StatementCSS")
    public void StatementCSS(@RequestBody CStatement cStatement) {
        workFlowGZService.statementCSS(cStatement);
    }

    /**
     * 工单撤单
     */

    @PostMapping("/WithdrawCSS")
    public void WithdrawCSS(@RequestBody CWithdraw cWithdraw) {
        workFlowGZService.withdrawCSS(cWithdraw);
    }

    /**
     * 工单再处理
     */

    @PostMapping("/ReprocessCSS")
    public void ReprocessCSS(@RequestBody CReprocess cReprocess) {
        workFlowGZService.reprocessCSS(cReprocess);
    }

    /**
     * 工单催办
     */

    @PostMapping("/UrgeCSS")
    public void UrgeCSS(@RequestBody CUrge cUrge) {
        workFlowGZService.urgeCSS(cUrge);
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
    public void CurrencyCSS(@RequestBody CCurrency cCurrency) {
        workFlowGZService.currencyCSS(cCurrency);
    }

    /**
     * 数据同步 省内调用集团地址进行推送数据
     */
    @PostMapping("/SyncData")
    public void SyncData(@RequestBody CSyncData cSyncData) {
        workFlowGZService.syncData(cSyncData);
    }

    /**
     * 测试工单同步删除接口
     *
     * @param workFlowF WorkFlowF
     */

    @PostMapping("/TestjobCSS")
    public void TestJobCSS(@RequestBody WorkFlowF workFlowF) {
        workFlowGZService.TestJobCSS(workFlowF);
    }

    /**
     * 附件上传到集团服务器
     */
    @PostMapping("/upload")
    public void upload(MultipartFile file) {
        R.ok(workFlowGZService.uploadToJT(file));
    }

    /**
     * 重新刷新拉取来自集团的附件
     */
    @GetMapping("/download")
    public void downloadFile(String path, @RequestParam(defaultValue = CSVC.UNIT_SN) String unit) throws Exception {
        //拉取集团附件，集团附件可能需要从集团服务器刷新拉取
        if (StrUtil.equals(CSVC.UNIT_JT, unit)) {
            FileUtil.download(path);
        } else {
            //拉取本省生成的工单附件，直接在本省服务器上拉取
        }
        R.ok();
    }

    /**
     * 获取附件上传校验文件
     */
    @GetMapping("/getUploadCheckFile")
    public void getUploadCheckFile() {
        workFlowGZService.getUploadCheckFile();
    }

}
