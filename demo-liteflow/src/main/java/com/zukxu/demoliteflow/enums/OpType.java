package com.zukxu.demoliteflow.enums;

import lombok.Getter;

/**
 * <p>
 * 工单接口服务类别枚举
 * </p>
 *
 * @author xupu
 * @since 2022-09-25 00:00
 */
@Getter
public enum OpType {
    DispatchCSS(1, "工单派发"),
    ReplyCSS(2, "工单回复"),
    StatementCSS(3, "工单归档"),
    WithdrawCSS(4, "工单撤单"),
    QueryCSS(5, "工单查询"),
    ReprocessCSS(6, "工单再处理"),
    UrgeCSS(7, "工单催办"),
    CurrencyCSS(8, "工单通用接口"),
    SyncData(9, "工单信息同步"),
    TestjobCSS(10, "测试工单同步删除");

    private int value;


    private String name;

    OpType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getName(int value) {
        for(OpType c : OpType.values()) {
            if(c.getValue() == value) {
                return c.name;
            }
        }
        return null;
    }
}

