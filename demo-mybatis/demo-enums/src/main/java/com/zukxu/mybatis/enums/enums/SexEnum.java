package com.zukxu.mybatis.enums.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

@Getter
public enum SexEnum implements IEnum<String> {
    MAIL("1", "男"), FEMAIL("2", "女"), OTHER("3", "未知");

    private final String value;
    private final String desc;

    SexEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }
}
