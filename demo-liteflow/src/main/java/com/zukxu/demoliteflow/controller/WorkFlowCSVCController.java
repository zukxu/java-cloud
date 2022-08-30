package com.zukxu.demoliteflow.controller;

import com.alibaba.fastjson.JSON;
import com.bonc.flowable.model.csvc.WorkFlowF;
import com.bonc.flowable.service.WorkFlowCSVCService;
import com.bonc.system.log.annotation.LogAnnotation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 集团调用我们的省分大音平台
 */
@RestController
@RequestMapping({ "/sync/CSVC" })
public class WorkFlowCSVCController {

    @Resource
    private WorkFlowCSVCService workFlowCSVCService;


    /**
     * 集团调用省分 派单入库省分
     *
     * @param param
     *
     * @return
     */
    @LogAnnotation(module = "sync/CSVC")
    @PostMapping({ "/DispatchCSS" })
    public String DispatchCSS(@RequestBody HashMap param) {
        return JSON.toJSONString(workFlowCSVCService.dispatchCSS(param));
    }


    /**
     * 集团调用省分 回复
     *
     * @param param
     *
     * @return
     */
    @LogAnnotation(module = "sync/CSVC")
    @PostMapping({ "/ReplyCSS" })
    public String ReplyCSS(@RequestBody HashMap param) {
        return JSON.toJSONString(workFlowCSVCService.replyCSS(param));
    }

    @LogAnnotation(module = "sync/CSVC")
    @PostMapping({ "/QueryCSS" })
    public String QueryCSS(@RequestBody HashMap param) {
        return JSON.toJSONString(workFlowCSVCService.queryCSS(param));
    }

    /**
     * 集团调用省分 归档
     *
     * @param param
     *
     * @return
     */
    @LogAnnotation(module = "sync/CSVC")
    @PostMapping({ "/StatementCSS" })
    public String StatementCSS(@RequestBody HashMap param) {
        return JSON.toJSONString(workFlowCSVCService.statementCSS(param));
    }

    /**
     * 集团调用省分 撤单
     *
     * @param param
     *
     * @return
     */
    @LogAnnotation(module = "sync/CSVC")
    @PostMapping({ "/WithdrawCSS" })
    public String WithdrawCSS(@RequestBody HashMap param) {
        return JSON.toJSONString(workFlowCSVCService.withdrawCSS(param));
    }

    @LogAnnotation(module = "sync/CSVC")
    @PostMapping({ "/ReprocessCSS" })
    public String ReprocessCSS(@RequestBody HashMap param) {
        return JSON.toJSONString(workFlowCSVCService.reprocessCSS(param));
    }

    @LogAnnotation(module = "sync/CSVC")
    @PostMapping({ "/UrgeCSS" })
    public String UrgeCSS(@RequestBody HashMap param) {
        return JSON.toJSONString(workFlowCSVCService.urgeCSS(param));
    }

    /**
     * <p>
     * 工单通用接口
     * </p>
     *
     * @author xupu
     * @since 2022/3/31 11:22
     */
    @LogAnnotation(module = "sync/CSVC")
    @PostMapping("/CurrencyCSS")
    public String CurrencyCSS(@RequestBody HashMap param) {
        return JSON.toJSONString(workFlowCSVCService.currencyCSS(param));
    }

    /**
     * 测试工单同步删除接口
     *
     * @param workFlowF WorkFlowF
     *
     * @return R
     */
    @LogAnnotation(module = "sync/CSVC")
    @PostMapping("/TestjobCSS")
    public void TestJobCSS(@RequestBody WorkFlowF workFlowF) {
        workFlowCSVCService.TestJobCSS(workFlowF);
    }

}
