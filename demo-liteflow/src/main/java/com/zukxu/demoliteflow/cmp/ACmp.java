package com.zukxu.demoliteflow.cmp;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.Slot;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022/7/28 14:33:15
 */
@LiteflowComponent(id = "a",name = "ACmp")
public class ACmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        System.out.println("Running A …………");
    }

    @Override
    public boolean isAccess() {
        System.out.println("是否进入该节点预先判断");
        return super.isAccess();
    }

    @Override
    public boolean isContinueOnError() {
        System.out.println("是否在错误时继续往下执行，默认false");
        return super.isContinueOnError();
    }

    @Override
    public boolean isEnd() {
        System.out.println("返回true，表示该节点执行完毕马上结束");
        System.out.println("由于是用户主动结束的流程，属于正常结束，所以最终的isSuccess是为true");
        return super.isEnd();
    }

    @Override
    public <T> void beforeProcess(String nodeId, Slot slot) {
        System.out.println("流程前置处理器，在isAccess方法后执行");
        System.out.println("通用前置和后置处理，推荐使用切面");
        super.beforeProcess(nodeId, slot);
    }

    @Override
    public <T> void afterProcess(String nodeId, Slot slot) {
        System.out.println("流程后置处理器");
        super.afterProcess(nodeId, slot);
    }

    @Override
    public void onSuccess() throws Exception {
        System.out.println("组件成功回调");
        super.onSuccess();
    }

    @Override
    public void onError() throws Exception {
        System.out.println("组件失败回调");
        super.onError();
    }

}
