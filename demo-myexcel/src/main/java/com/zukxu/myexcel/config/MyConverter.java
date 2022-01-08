package com.zukxu.myexcel.config;

import com.github.liaochong.myexcel.core.converter.CustomWriteContext;
import com.github.liaochong.myexcel.core.converter.CustomWriteConverter;

/**
 * 自定义转换类
 */
public class MyConverter implements CustomWriteConverter<Object, Object> {


    @Override
    public Object convert(Object originalData, CustomWriteContext customWriteContext) {
        return originalData;
    }

}