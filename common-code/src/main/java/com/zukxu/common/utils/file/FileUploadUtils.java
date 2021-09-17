package com.zukxu.common.utils.file;

import cn.hutool.crypto.digest.DigestUtil;
import com.zukxu.common.exception.BaseException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xupu
 * @Description 文件上传工具类
 * @Date 2021-09-17 9:33
 */
public class FileUploadUtils {
    /**
     * 上传文件
     *
     * @param file       文件
     * @param uploadPath 上传路径
     * @return map
     */
    public static Map<String, Object> uploadFile(MultipartFile file, String uploadPath) {
        if (file == null) {
            throw new BaseException("文件不存在");
        }
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        // 文件md5值
        String fileMd5 = getFileMD5(file);
        String newFileName = fileMd5 + "." + fileSuffix;
        long fileSize = file.getSize() / 1024;

        // 路径格式转换
        uploadPath = uploadPath.replace("\\", "/");

        // 按照日期自动生成3级目录
        LocalDate date = LocalDate.now();
        // 根据日期自动创建3级目录
        String dateFolder = new SimpleDateFormat("yyyy/MM/dd").format(date);
        // 设置文件存储目录
        uploadPath = uploadPath + "/" + dateFolder;


        // 自动创建文件夹
        File f = new File(uploadPath);
        if (f.isDirectory() && !f.exists()) f.mkdirs();

        try {
            // 将内存中的数据写入磁盘
            String absolutePath = f.getAbsolutePath();
            File newFile = new File(absolutePath, newFileName);
            file.transferTo(newFile);

            // 返回map
            Map<String, Object> map = new HashMap<>();
            map.put("oldFileName", originalFilename);// 文件原名称
            map.put("newFileName", newFileName);     // 文件MD5+后缀（新名称）
            if (fileSize < 1024) {
                map.put("fileSize", fileSize + " KB");// 文件大小（带单位）
            } else if (fileSize < 1024 * 1024) {
                map.put("fileSize", fileSize / 1024 + " M");
            } else {
                map.put("fileSize", fileSize / (1024 * 1024) + " G");
            }
            map.put("fileSuffix", fileSuffix);// 文件后缀
            map.put("fileUrl", uploadPath + "/" + newFileName);// 文件路径
            return map;
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
    }


    /**
     * 获取文件的MD5值
     *
     * @param file
     * @return
     */
    private static String getFileMD5(MultipartFile file) {
        String fileMd5 = "";

        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fileItem = (DiskFileItem) cf.getFileItem();
        String targetFile = fileItem.getStoreLocation().toString();
        try {
            fileMd5 = DigestUtil.md5Hex(new FileInputStream(targetFile));
        } catch (Exception e) {
            fileMd5 = UUID.randomUUID().toString().replace("-", "");
        }

        return fileMd5;
    }
}