package com.zukxu.demoliteflow.test.cmp;


import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.zukxu.demoliteflow.test.context.BatchMessageResultContext;

@LiteflowComponent(id = "channel5", name = "返回渠道5")
public class Channel5Cmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        BatchMessageResultContext context = this.getFirstContextBean();
        context.setFinalResultChannel("channel5");
    }

}
