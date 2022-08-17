package com.zukxu.demoliteflow.cmp.chose;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;

import java.util.Random;

/**
 * <p>
 * 是否立即派发组件
 * </p>
 *
 * @author xupu
 * @since 2022/8/17 15:21:43
 */
@LiteflowComponent(id = "IfDis", name = "是否立即派发")
public class IFDisNowSwitchCmp extends NodeSwitchComponent {

    @Override
    public String processSwitch() throws Exception {
        //模拟业务耗时
        int time = new Random().nextInt(1000);
        Thread.sleep(time);

        String result = "jt";
        return result;
    }

}