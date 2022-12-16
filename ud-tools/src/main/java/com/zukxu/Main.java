package com.zukxu;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import cn.hutool.setting.dialect.PropsUtil;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

/**
 * <p>
 * ${END}
 * </p>
 *
 * @author xupu
 * @since ${DATE} ${TIME}:${SECOND}
 */
public class Main {

    //@formatter:off
    private static final String suffix = ".vmoptions";
    private static final String CONFIG_FILE_NAME = "idea.properties";
    private static final Map<String, String> PATH_MAP = new HashMap<>();
    private static final Map<String, String> CONFIG_MAP = new HashMap<String, String>() {
        {
            put("idea", ".IntelliJIdea");
            put("webstorm", ".WebStorm");
            put("datagrip", ".DataGrip");
            put("pycharm", ".Pycharm");
        }
    };
    private static final List<String> CONFIG_LIST = new ArrayList<String>() {
        {
            add("idea.config.path=${software.path}/{xx}/config");
            add("idea.system.path=${software.path}/{xx}/system");
            add("idea.plugins.path=${idea.config.path}/plugins");
            add("idea.log.path=${idea.system.path}/log");
        }
    };
    //@formatter:on

    public static void main(String[] args) {
        //获取配置项
        Props props = PropsUtil.get("config");
        String install = props.getStr("install.path");
        String update = props.getStr("update.path");
        //初始化相关配置
        initPathPam(install);
        //更新配置文件
        PATH_MAP.forEach((k, v) -> {
            String s = v + "/" + "bin" + "/" + CONFIG_FILE_NAME;
            Props firstFound = PropsUtil.getFirstFound(s);
            firstFound.setProperty("software.path", install);
            CONFIG_LIST.forEach(c -> {
                String[] split = c.split("=");
                firstFound.setProperty(split[0], split[1].replace("{xx}", CONFIG_MAP.get(k.toLowerCase())));
            });
            firstFound.store(s);
        });

    }

    private static void initPathPam(String install) {
        File dir = new File(install);
        if(!FileUtil.isDirectory(dir)) System.out.println("不是一个目录");
        File[] files = dir.listFiles(t -> t.isDirectory() && !t.getName().startsWith(".") && Objects.requireNonNull(t.listFiles()).length > 0);
        Stream.of(Optional.ofNullable(files).orElse(new File[]{})).forEach(t -> {
            File[] cf = FileUtil.file(t.getPath(), "ch-0").listFiles(f -> f.getName().endsWith(suffix));
            if(ArrayUtil.isNotEmpty(cf)) {
                PATH_MAP.put(StrUtil.subBefore(t.getName(), "-", true), StrUtil.subBefore(cf[0].getPath(), suffix, true));
            }
        });
    }

}