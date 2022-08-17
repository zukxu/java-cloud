package com.zukxu.demoliteflow.context;

import java.util.Map;

/**
 * <p>
 * 工单上下文
 * </p>
 *
 * @author xupu
 * @since 2022/8/1 17:32:08
 */
public class WorkFlowContext {

    //@formatter:off
    private Map<String, Object> workFlow;
    private String status;
    //@formatter:on


    public Map<String, Object> getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(Map<String, Object> workFlow) {
        this.workFlow = workFlow;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
