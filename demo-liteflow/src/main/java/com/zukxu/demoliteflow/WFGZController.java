package com.zukxu.demoliteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.zukxu.demoliteflow.context.ChainConst;
import com.zukxu.demoliteflow.context.WorkFlowContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/sync/gz")
@RequiredArgsConstructor
@Slf4j
public class WFGZController {

    //@formatter:off
    private final FlowExecutor flowExecutor;
    //@formatter:on

    /**
     * 省分派发申请单
     *{
     *     "Identifier": "20220727GZL85107961345",
     *     "IdentyType": "04",
     *     "IdentySubtype": "0401",
     *     "IdentyDetail": null,
     *     "Title": "调研:测试2222",
     *     "Content": "asdf",
     *     "OriginUnit": "851",
     *     "ReceiverUnit": "851",
     *     "CreatTime": "20220727163334",
     *     "ProcessTime": null,
     *     "Creator": "admin",
     *     "CreatorContactInfo": null,
     *     "AttachList": null,
     *     "ParaList": null
     * }
     * @return R
     */

    @PostMapping("/DispatchCSS")
    public String DispatchCSS(@RequestBody Map<String, Object> paramMap) {
        LiteflowResponse response = flowExecutor.execute2Resp(ChainConst.SN_DIS, paramMap, WorkFlowContext.class);
        WorkFlowContext context = response.getFirstContextBean();
        if(response.isSuccess()) {
            log.info("执行成功:{}", context.getWorkFlow());
        } else {
            log.error("执行失败", response.getCause());
        }
        return "Dispatch";
    }


    /**
     * 工单回复
     *
     * @return R
     */

    @PostMapping("/ReplyCSS")
    public String ReplyCSS(@RequestBody Map<String, Object> paramMap) {
        return "Reply";
    }

    /**
     * 工单查询
     *
     * @return R
     */

    @PostMapping("/QueryCSS")
    public String QueryCSS(@RequestBody Map<String, Object> paramMap) {
        return "Query";
    }

    /**
     * 工单归档
     *
     * @return R
     */

    @PostMapping("/StatementCSS")
    public String StatementCSS(@RequestBody Map<String, Object> paramMap) {
        return "Statement";
    }

    /**
     * 工单撤单
     *
     * @return R
     */

    @PostMapping("/WithdrawCSS")
    public String WithdrawCSS(@RequestBody Map<String, Object> paramMap) {
        return "Withdraw";
    }

    /**
     * 工单再处理
     *
     * @return R
     */

    @PostMapping("/ReprocessCSS")
    public String ReprocessCSS(@RequestBody Map<String, Object> paramMap) {
        return "Reprocess";
    }

    /**
     * 工单催办
     *
     * @return R
     */

    @PostMapping("/UrgeCSS")
    public String UrgeCSS(@RequestBody Map<String, Object> paramMap) {
        return "Urge";
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
    public String CurrencyCSS(@RequestBody Map<String, Object> paramMap) {
        return "Current";
    }

    /**
     * 数据同步 省内调用集团地址进行推送数据
     *
     * @return R
     */

    @PostMapping("/SyncData")
    public String SyncData(@RequestBody Map<String, Object> paramMap) {
        return "SyncData";
    }

}
