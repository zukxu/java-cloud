package com.zukxu.demoliteflow.context;

import com.zukxu.demoliteflow.model.WorkFlowF;

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
    private String status;
    private WorkFlowF workFlowF;
    private Map<String, Object> variables;
    //@formatter:on

    public WorkFlowF getWorkFlowF() {
        return workFlowF;
    }

    public void setWorkFlowF(WorkFlowF workFlowF) {
        this.workFlowF = workFlowF;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
