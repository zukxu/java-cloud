package com.zukxu.myexcel.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2021-12-23 19:31
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrgTypeEnum {
    PHONE("phone", "手机上网"),
    G5("5g", "5G"),
    BROADBAND("broadband", "宽带"),
    JCR("jcr", "决策人"),
    LXR("lxr", "联系人"),
    ZX("zx", "专线"),
    QK("qk", "企宽"),
    GYY("gyy", "公有云");

    /**
     * 类型
     */
    private String type;
    /**
     * 信息
     */
    private String info;

    OrgTypeEnum(String type, String info) {
        this.type = type;
        this.info = info;
    }

    /**
     * **根据value值获取枚举对象**
     *
     * @param value
     */
    public static OrgTypeEnum getEnum(String value) {
        OrgTypeEnum[] orgTypeArr = values();

        for (OrgTypeEnum orgType : orgTypeArr) {
            if (orgType.getType().equals(value)) {
                return orgType;
            }
        }

        return null;
    }

    public static String[] getTypeArray() {
        return new String[]{G5.getType(), PHONE.getType(), BROADBAND.getType()};
    }

    public static String[] getZQTypeArray() {
        return new String[]{JCR.getType(), LXR.getType(), ZX.getType(), QK.getType(), GYY.getType()};
    }
}
