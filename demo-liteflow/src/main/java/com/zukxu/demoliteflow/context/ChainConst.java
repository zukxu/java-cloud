package com.zukxu.demoliteflow.context;

/**
 * <p>
 * 编排流程链路id
 * </p>
 *
 * @author xupu
 * @since 2022/8/17 17:04:04
 */
public interface ChainConst {

    /**
     * 省内派单流程
     */
    String SN_DIS = "sn_dis";

    /**
     * 省内处理流程
     */
    String SN_HANDLER = "sn_handler";

    /**
     * 集团派单，省分接单流程
     */
    String JT_SN_DIS = "jt_dis";

    /**
     * 省分回复集团
     */
    String SN_JT_REPLY = "sn_jt_reply";

    /**
     * 集团再处理省分
     */
    String JT_REPROCESS = "jt_reprocess";

    /**
     * 组件名称
     */
    //@formatter:off
    String BIND_PROCESS = "BindProcess";
    String CREATE_WF = "CreateWF";
    String CURRENCY = "Currency";
    String DISPATCH = "Dispatch";
    String HANDLER_WF = "HandlerWF";
    String NO_HANDLER = "NoHandler";
    String QUERY = "Query";
    String REPLY = "Reply";
    String REPROCESS = "Reprocess";
    String STATEMENT = "Statement";
    String SYNC_DATA = "SyncData";
    String URGE = "Urge";
    String WITHDRAW = "Withdraw";
    String JT = "jt";
    String SN = "sn";
    //@formatter:on
}
