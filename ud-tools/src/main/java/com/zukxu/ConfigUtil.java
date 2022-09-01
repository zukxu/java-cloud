package com.zukxu;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 文件工具类
 * </p>
 *
 * @author xupu
 * @since 2022/9/1 10:23:56
 */
public class ConfigUtil {

    private ConfigUtil() {}

    /**
     * 读取jar包内部配置文件
     */
    public static String readConfig() {
        ClassPathResource resource = new ClassPathResource("config.json");
        return IoUtil.read(resource.getStream(), StandardCharsets.UTF_8);
    }

    /**
     * 读取jar包外部配置文件
     */
    public static String readOutConfig() {
        try {
            String fileName = System.getProperty("user.dir") + File.separator + "config.json";
            String jsonStr;
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch;
            StringBuffer sb = new StringBuffer();
            while((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;

        } catch(FileNotFoundException e) {
            System.out.println("外部配置文件读取错误：" + e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

