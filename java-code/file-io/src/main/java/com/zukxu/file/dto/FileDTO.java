package com.zukxu.file.dto;

import lombok.Data;

/**
 * @author xupu
 * @Description 文件信息保存实体
 * @Date 2021-09-16 17:50
 */
@Data
public class FileDTO {
    /**
     * 文件名
     */
    private String name;
    /**
     * 图片远程访问url
     */
    private String url;
    /**
     * 本地存储地址
     */
    private String uploadPath;
}
