package com.zukxu.demoliteflow.enums;

import lombok.Getter;

/**
 * <p>
 * 通用接口-接口类型枚举
 * </p>
 *
 * @author xupu
 * @since 2022/8/29 16:02:40
 */
@Getter
public enum InterfaceTypeEnum {
    Refund("退单转派"),
    HandlingComment("中途意见");

    private String details;

    InterfaceTypeEnum(String details) {
        this.details = details;
    }
}
