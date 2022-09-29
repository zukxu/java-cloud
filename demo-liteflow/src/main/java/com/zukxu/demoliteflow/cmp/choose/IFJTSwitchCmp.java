package com.zukxu.demoliteflow.cmp.choose;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;
import com.zukxu.demoliteflow.context.WorkFlowContext;

import java.util.Map;
import java.util.Random;

/**
 * <p>
 * 是否工单外派组件
 * </p>
 *
 * @author xupu
 * @since 2022/8/17 15:21:43
 */
@LiteflowComponent(id = "IfJT", name = "是否外派单子")
public class IFJTSwitchCmp extends NodeSwitchComponent {


    @Override
    public String processSwitch() throws Exception {
        WorkFlowContext context = this.getFirstContextBean();
        Map<String, Object> data = this.getRequestData();
        //context.setWorkFlow(data);
        System.out.println(this.getNodeId());
        System.out.println(this.getName());
        System.out.println(this.getChainName());
        //模拟业务耗时
        int time = new Random().nextInt(1000);
        Thread.sleep(time);
        String result = "jt";
        return result;
    }

}