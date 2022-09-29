package com.zukxu.demoliteflow.cmp.choose;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;
import com.zukxu.demoliteflow.context.ChainConst;

import java.util.Random;

/**
 * <p>
 * 是否同步组件
 * </p>
 *
 * @author xupu
 * @since 2022/8/17 15:21:43
 */
@LiteflowComponent(id = "IfSync", name = "是否同步")
public class IFSyncSwitchCmp extends NodeSwitchComponent {

@Override
public String processSwitch() throws Exception {
    //模拟业务耗时
    int time = new Random().nextInt(1000);
    Thread.sleep(time);

    String result = ChainConst.SYNC_DATA;
    return result;
}

}