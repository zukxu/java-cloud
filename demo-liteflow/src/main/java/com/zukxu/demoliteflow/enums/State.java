package com.zukxu.demoliteflow.enums;

import lombok.Getter;

/**
 * <p>
 * 工单状态枚举类
 * </p>
 *
 * @author xupu
 * @since 2022/6/15 10:28:41
 */
@Getter
public enum State {
    /**
     * 0-默认
     */
    DEFAULT("0", "默认"),
    /**
     * 1-转派
     */
    DISPATCH("1", "转派"),
    /**
     * 2-撤回
     */
    WITHDRAWN("2", "撤回"),
    /**
     * 3-通过
     */
    PASS("3", "通过"),
    /**
     * 4-催办
     */
    URGED("4", "催办"),
    /**
     * 5-完成
     */
    COMPLETE("5", "完成"),
    /**
     * 00：系统无此工单
     */
    NO_INFO("00", "系统无此工单"),
    /**
     * 01：处理中
     */
    PROCESSING("01", "处理中"),
    /**
     * 02：已退回
     */
    RETURNED("02", "已退回"),
    /**
     * 03：已撤回
     */
    HAS_WITHDRAWN("03", "已撤回"),
    /**
     * 04：已回复
     */
    REPLIED("04", "已回复"),
    /**
     * 05：已归档
     */
    ARCHIVED("05", "已归档"),
    /**
     * 06：已催办
     */
    HAS_URGE("06", "已催办"),
    /**
     * 省内未读
     */
    SN_UNREAD("11", "省内未读"),
    /**
     * 省内已读
     */
    SN_READ("12", "省内已读");

    private String state;

    private String stateName;

    State(String state, String stateName) {
        this.state = state;
        this.stateName = stateName;
    }
}
