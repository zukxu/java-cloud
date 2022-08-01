package com.zukxu.demoliteflow.test.cmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/28 14:33:15
 */
@LiteflowComponent(id = "as",name = "ASCmp")
public class ASwitchCmp extends NodeSwitchComponent {

    @Override
    public String processSwitch() throws Exception {
        System.out.println("ASCmp executed!");
        return "c";
    }

}
