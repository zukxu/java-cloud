package com.zukxu.myexcel.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-23 19:32
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TenantEnum {
    JT("jt", "集团"),
    SN("sn", "省内");
    /**
     * 类型
     */
    private String type;
    /**
     * 信息
     */
    private String info;

    TenantEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }
}
