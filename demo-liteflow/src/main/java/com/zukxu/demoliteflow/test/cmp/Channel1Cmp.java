package com.zukxu.demoliteflow.test.cmp;


import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.zukxu.demoliteflow.test.context.BatchMessageResultContext;

@LiteflowComponent(id = "channel1", name = "返回渠道1")
public class Channel1Cmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        BatchMessageResultContext context = this.getFirstContextBean();
        context.setFinalResultChannel("channel1");
    }

}
