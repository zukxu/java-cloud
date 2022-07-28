package com.zukxu.demoliteflow.cmp;


import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.zukxu.demoliteflow.context.BatchMessageResultContext;
import com.zukxu.demoliteflow.vo.QueryVO;

import java.util.List;

@LiteflowComponent(id = "channelSelector", name = "渠道余量最大选择器")
public class ChannelSelectorCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        BatchMessageResultContext context = this.getFirstContextBean();

        List<QueryVO> queryList = context.getQueryResultList();

        //选择渠道余量最大的
        QueryVO vo = queryList.stream().min((o1, o2) -> o2.getAvailCount() - o1.getAvailCount()).orElse(null);

        assert vo != null;
        context.setFinalResultChannel(vo.getChannel());
    }

}
