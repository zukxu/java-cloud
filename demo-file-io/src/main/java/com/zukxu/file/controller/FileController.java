package com.zukxu.file.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.zukxu.common.constant.FileConst;
import com.zukxu.common.result.R;
import com.zukxu.file.config.FileConfigProperties;
import com.zukxu.file.model.entity.DKAttachment;
import com.zukxu.file.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author xupu
 * @Description 上传
 * @Date 2021-09-16 17:51
 */
@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileConfigProperties fileConfigProperties;

    @PostMapping({ "/upload" })
    @ResponseBody
    public R<?> uploadFiles(MultipartFile file) {
        //上传至指定文件夹
        String originalFilename = file.getOriginalFilename();
        //文件新名称
        String suffix = FileUtil.getSuffix(originalFilename);
        String newFileName = FileConst.FILE_NAME_PREFIX + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + suffix;
        log.info("重命名文件：===>{}", newFileName);
        //设置文件存储路径，可以存放在你想要指定的路径里面
        String filePath = fileConfigProperties.getUploadPath() + File.separator + newFileName;
        File newFile = new File(filePath);
        //判断目标文件所在目录是否存在
        if(!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            boolean mkdirs = newFile.getParentFile().mkdirs();
        }

        //将内存中的数据写入磁盘
        try {
            //使用此方法保存必须要绝对路径且文件夹必须已存在,否则报错
            file.transferTo(newFile);
            log.info("文件上传完毕===>{}", newFile.getAbsolutePath());
        } catch(IOException e) {
            e.printStackTrace();
            log.info("文件上传失败");
        }

        DKAttachment dkAttachment = new DKAttachment();
        dkAttachment.setOriginName(originalFilename)
                    .setUrl(fileConfigProperties.getReturnPath() + "/" + newFileName);
        return R.ok(dkAttachment);
    }

    /**
     * 删除
     *
     * @param name
     */
    @DeleteMapping("/delete/{name}")
    public R<?> delete(@PathVariable("name") String name) {
        return R.ok(FileUtil.del(fileConfigProperties.getUploadPath() + File.separator + name));
    }

    /**
     * 下载
     *
     * @param name
     */
    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response, String name) {
        FileUtils.downloadFile(fileConfigProperties.getUploadPath(), name, request, response);
    }

    /**
     * 断点上传
     */
    @PostMapping({ "/breakPoint/upload" })
    @ResponseBody
    public R<?> breakPointUploadFiles(MultipartFile file) {
        //上传至指定文件夹
        String originalFilename = file.getOriginalFilename();
        //文件新名称
        String suffix = FileUtil.getSuffix(originalFilename);
        String newFileName = FileConst.FILE_NAME_PREFIX + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + suffix;
        log.info("重命名文件：===>{}", newFileName);
        //设置文件存储路径，可以存放在你想要指定的路径里面
        String filePath = fileConfigProperties.getUploadPath() + File.separator + newFileName;
        File newFile = new File(filePath);
        //判断目标文件所在目录是否存在
        if(!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            boolean mkdirs = newFile.getParentFile().mkdirs();
        }

        //将内存中的数据写入磁盘
        try {
            //使用此方法保存必须要绝对路径且文件夹必须已存在,否则报错
            file.transferTo(newFile);
            log.info("文件上传完毕===>{}", newFile.getAbsolutePath());
        } catch(IOException e) {
            e.printStackTrace();
            log.info("文件上传失败");
        }

        DKAttachment dkAttachment = new DKAttachment();
        dkAttachment.setOriginName(originalFilename)
                    .setUrl(fileConfigProperties.getReturnPath() + "/" + newFileName);
        return R.ok(dkAttachment);
    }

}
