package com.zukxu.controller;

import cn.xuyanwu.spring.file.storage.FileInfo;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 请求接口
 * </p>
 *
 * @author xupu
 * @since 2023/10/17 10:28:10
 */
@RestController
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;//注入实列

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public FileInfo upload(MultipartFile file) {
        return fileStorageService.of(file).upload();
    }

    /**
     * 上传文件，成功返回文件 url
     */
    @PostMapping("/upload2")
    public String upload2(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file).setPath("upload/") //保存到相对路径下，为了方便管理，不需要可以不写
                                              .setObjectId("0")   //关联对象id，为了方便管理，不需要可以不写
                                              .setObjectType("0") //关联对象类型，为了方便管理，不需要可以不写
                                              .putAttr("role", "admin") //保存一些属性，可以在切面、保存上传记录、自定义存储平台等地方获取使用，不需要可以不写
                                              .upload();  //将文件上传到对应地方
        return fileInfo == null ? "上传失败！" : fileInfo.getUrl();
    }

    /**
     * 上传图片，成功返回文件信息
     * 图片处理使用的是 https://github.com/coobird/thumbnailator
     */
    @PostMapping("/upload-image")
    public FileInfo uploadImage(MultipartFile file) {
        return fileStorageService.of(file).image(img -> img.size(1000, 1000))  //将图片大小调整到 1000*1000
                                 .thumbnail(th -> th.size(200, 200))  //再生成一张 200*200 的缩略图
                                 .upload();
    }

    /**
     * 上传文件到指定存储平台，成功返回文件信息
     */
    @PostMapping("/upload-platform")
    public FileInfo uploadPlatform(MultipartFile file) {
        return fileStorageService.of(file).setPlatform("aliyun-oss-1")    //使用指定的存储平台
                                 .upload();
    }
}

