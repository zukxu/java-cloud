package com.zukxu.demoliteflow.cmp;


import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.zukxu.demoliteflow.context.BatchMessageResultContext;

@LiteflowComponent(id = "channel6", name = "返回渠道6")
public class Channel6Cmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        BatchMessageResultContext context = this.getFirstContextBean();
        context.setFinalResultChannel("channel6");
    }

}
