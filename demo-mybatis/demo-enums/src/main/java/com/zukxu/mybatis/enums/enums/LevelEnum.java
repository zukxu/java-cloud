package com.zukxu.mybatis.enums.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum LevelEnum {

    PROVINCE("1", "省份"),
    CITY("2", "地市"),
    COUNTRY("3", "区县"),
    GRID("4", "网格");

    LevelEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    @JsonValue
    private final String code;
    private final String desc;

}
