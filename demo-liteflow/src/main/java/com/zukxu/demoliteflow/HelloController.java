package com.zukxu.demoliteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.zukxu.demoliteflow.test.context.WorkFlowContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/liteFlow")
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    //@formatter:off
    private final ChainExecute chainExecute;
    private final FlowExecutor flowExecutor;
    //@formatter:on
    private static Map<String, Object> chainMap = new HashMap<>();

    static {
        chainMap.put("SN_SN_DIS", "04");
        chainMap.put("SN_SN_DIS_SYNC", "0204,0205,0206");
        chainMap.put("SN_JT_DIS", "");
        chainMap.put("SN_JT_DIS_TRIAL", "0302");
        chainMap.put("JT_DIS", "");
        chainMap.put("JT_DIS_OTHER", "");
    }

    @GetMapping("/hello1")
    public void hello1() {
        chainExecute.testConfig();
    }

    @GetMapping("/hello2")
    public void hello2(@RequestParam String type) {

        String chainId = "SN_SN_DIS";
        Map<String, Object> params = new HashMap<>();
        //第二个参数为流程入参，示例中没用到，所以传null，实际业务是有值的
        LiteflowResponse response = flowExecutor.execute2Resp(chainId, params, WorkFlowContext.class);
        WorkFlowContext context = response.getFirstContextBean();
        if(response.isSuccess()) {
            log.info("执行成功，类型为{}", context.getWorkFlowType());
        } else {
            log.error("执行失败", response.getCause());
        }
    }

}
