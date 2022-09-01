package com.zukxu;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

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
    private static final String charset = CharsetUtil.UTF_8;
    private static final Map<String, String> pathMap = new HashMap<>();
    private static final Map<String, String> configMap = new HashMap<String, String>() {
        {
            put("idea", ".IntelliJIdea");
            put("webstorm", ".WebStorm");
            put("datagrip", ".DataGrip");
        }
    };
    private static final List<String> configList = new ArrayList<String>() {
        {
            add("software.path={xxx}");
            add("idea.config.path=${software.path}/{xx}/config");
            add("idea.system.path=${software.path}/{xx}/system");
            add("idea.plugins.path=${idea.config.path}/plugins");
            add("idea.log.path=${idea.system.path}/log");
        }
    };
    //@formatter:on

    public static void main(String[] args) {
        String config = ConfigUtil.readOutConfig();
        JSONObject obj;
        if(StrUtil.isNotBlank(config)) {
            obj = JSONUtil.parseObj(config);
        } else {
            obj = JSONUtil.parseObj(ConfigUtil.readConfig());
        }
        System.out.println("==========================");
        System.out.print("更新配置文件");
        System.out.println("==========================");
        System.out.println("开始执行...");
        String install = (String) JSONUtil.getByPath(obj, "install");
        String vmoptions = (String) JSONUtil.getByPath(obj, "vmoptions");
        handleVmoptions(install, vmoptions);
        handleProperties(install);
    }


    private static void handleProperties(String install) {
        pathMap.forEach((k, v) -> {
            System.out.println("更新" + k + "配置...");
            String configFile = v + "\\bin\\idea.properties";
            List<String> newList = new ArrayList<>();
            configList.forEach(t -> {
                String temp = t;
                if(t.contains("{xxx}")) temp = t.replace("{xxx}", install);
                if(t.contains("{xx}")) temp = t.replace("{xx}", configMap.get(k.toLowerCase()));
                newList.add(temp);
            });
            FileUtil.appendLines(newList, configFile, charset);
            System.out.println(configFile);
        });
    }

    private static void handleVmoptions(String install, String vmoptions) {
        File dir = new File(install);
        if(!FileUtil.isDirectory(dir)) System.out.println("不是一个目录");
        File[] files = dir.listFiles(t -> t.isDirectory() && !t.getName().startsWith(".") && Objects.requireNonNull(t.listFiles()).length > 0);
        Stream.of(Optional.ofNullable(files).orElse(new File[]{})).forEach(t -> {
            File[] cf = FileUtil.file(t.getPath(), "ch-0").listFiles(f -> f.getName().endsWith(suffix));
            if(ArrayUtil.isNotEmpty(cf)) {
                pathMap.put(StrUtil.subBefore(t.getName(), "-", true), StrUtil.subBefore(cf[0].getPath(), suffix, true));
            }
        });
        System.out.println(vmoptions);
        // 读取最新配置文件 更新旧的配置文件
        pathMap.forEach((k, v) -> {
            System.out.println("更新" + k + "vmoptions配置...");
            String updPath = vmoptions + File.separator + k.toLowerCase() + suffix;
            System.out.println(updPath);
            List<String> updLines = FileUtil.readLines(v + suffix, charset);
            List<String> oldLines = FileUtil.readLines(updPath, charset);
            List<String> agent = new ArrayList<>(oldLines.subList(oldLines.size() - 4, oldLines.size()));
            oldLines = oldLines.subList(0, oldLines.lastIndexOf("-Dide.no.platform.update=true"));
            oldLines.addAll(updLines);
            oldLines.addAll(agent);

            updPath = vmoptions + File.separator + k.toLowerCase() + suffix;
            FileUtil.writeLines(oldLines, updPath, charset);
        });
    }

}