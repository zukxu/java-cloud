package com.zukxu.upload_big_file.context;

import cn.hutool.core.collection.CollectionUtil;
import com.zukxu.upload_big_file.annotation.UploadMode;
import com.zukxu.upload_big_file.enu.UploadModeEnum;
import com.zukxu.upload_big_file.strategy.SliceUploadStrategy;
import com.zukxu.upload_big_file.util.SpringContextHolder;
import org.reflections.Reflections;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public enum UploadContext {
    INSTANCE;

    private static final String PACKAGE_NAME = "com.github.lybgeek.upload.strategy.impl";

    private final Map<UploadModeEnum, Class<SliceUploadStrategy>> uploadStrategyMap = new ConcurrentHashMap<>();

    public void init() {
        Reflections reflections = new Reflections(PACKAGE_NAME);
        Set<Class<?>> clzSet = reflections.getTypesAnnotatedWith(UploadMode.class);
        if (CollectionUtil.isNotEmpty(clzSet)) {
            for (Class<?> clz : clzSet) {
                UploadMode uploadMode = clz.getAnnotation(UploadMode.class);
                uploadStrategyMap.put(uploadMode.mode(), (Class<SliceUploadStrategy>) clz);
            }
        }
    }

    public SliceUploadStrategy getInstance(UploadModeEnum mode) {
        return this.getStrategyByType(mode);

    }

    private SliceUploadStrategy getStrategyByType(UploadModeEnum mode) {
        Class<SliceUploadStrategy> clz = uploadStrategyMap.get(mode);
        Assert.notNull(clz, "mode:" + mode + "can not found class,please checked");
        return SpringContextHolder.getBean(clz);
    }

}
