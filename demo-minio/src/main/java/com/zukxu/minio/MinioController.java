package com.zukxu.minio;

import com.alibaba.fastjson.JSON;
import com.zukxu.common.result.R;
import com.zukxu.minio.config.MinioProperties;
import com.zukxu.minio.utils.MinioUtils;
import io.minio.ObjectStat;
import io.minio.Result;
import io.minio.messages.Item;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * minio上传,下载,删除接口
 *
 * @author zukxu
 */
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Autowired
    private MinioProperties minio;
    @Autowired
    private MinioUtils minioUtils;

    /**
     * 计算文件大小
     *
     * @param size 大小
     * @return 大小
     */
    private static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (size == 0) {
            return wrongSize;
        }
        if (size < 1024) {
            fileSizeString = df.format((double) size) + " B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + " KB";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + " MB";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + " GB";
        }
        return fileSizeString;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 链接
     */
    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    @SneakyThrows
    public R upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new Exception("上传文件不能为空");
        } else {
            // 得到文件流
            final InputStream stream = file.getInputStream();
            // 文件名
            String fileName = file.getOriginalFilename();
            // 获取文件后缀名
            assert fileName != null;
            String filenameExtension = "." + fileName.substring(fileName.lastIndexOf(".") + 1);

            //时间戳做新的文件名，避免中文乱码-重新生成fileName
            fileName = System.currentTimeMillis() + filenameExtension;
            // 把文件放到minio的桶里面
            minioUtils.putObject(minio.getBucketName(), fileName, stream);
            String objectUrl = minioUtils.getObjectURL(minio.getBucketName(), fileName);
            // 关闭输入流
            stream.close();
            return R.ok(objectUrl);
        }
    }

    /**
     * 下载文件
     */
    @ApiOperation(value = "下载文件")
    @GetMapping(value = "/download/{fileName}")
    @SneakyThrows(Exception.class)
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        ObjectStat stat = minioUtils.statObject(minio.getBucketName(), fileName);
        response.setContentType(stat.contentType());
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        InputStream in = minioUtils.getObject(minio.getBucketName(), fileName);
        IOUtils.copy(in, response.getOutputStream());
        in.close();
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @return 封装
     * @author zukxu
     */
    @ApiOperation(value = "删除文件")
    @DeleteMapping(value = "/delete/{fileName}")
    @SneakyThrows(Exception.class)
    public R delete(@PathVariable("fileName") String fileName) {
        minioUtils.removeObject(minio.getBucketName(), fileName);
        return R.ok("删除成功");
    }

    /**
     * 根据桶名查询列表
     *
     * @return list
     */
    @ApiOperation(value = "查询所有文件")
    @GetMapping("/list")
    public List<Object> list(ModelMap map) throws Exception {
        Iterable<Result<Item>> myObjects = minioUtils.listObjects(minio.getBucketName());
        Iterator<Result<Item>> iterator = myObjects.iterator();
        List<Object> items = new ArrayList<>();
        String format = "{'fileName':'%s','fileSize':'%s'}";
        while (iterator.hasNext()) {
            Item item = iterator.next().get();
            items.add(JSON.parse(String.format(format, item.objectName(), formatFileSize(item.size()))));
        }
        return items;
    }
}
