package com.zukxu.file.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 文件上传工具类
 * </p>
 *
 * @author xupu
 * @since 2022-03-18 15:45
 */
public class FileUtils {

    final static Logger log = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {}

    /**
     * 下载文件
     *
     * @param path     文件的位置
     * @param fileName 自定义下载文件的名称
     * @param resp     http响应
     * @param req      http请求
     */
    public static void downloadFile(String path, String fileName, HttpServletRequest req, HttpServletResponse resp) {
        String charsetCode = String.valueOf(StandardCharsets.UTF_8);
        try {
            File file = new File(path + File.separator + fileName);
            //中文乱码解决
            String type = req.getHeader("User-Agent").toLowerCase();
            // 字符编码格式
            if(type.indexOf("firefox") > 0 || type.indexOf("chrome") > 0) {
                //谷歌或火狐
                fileName = new String(fileName.getBytes(charsetCode), "iso8859-1");
            } else {
                //IE
                fileName = URLEncoder.encode(fileName, charsetCode);
            }
            // 设置响应的头部信息
            resp.setHeader("content-disposition", "attachment;filename=" + fileName);
            // 设置响应内容的类型
            resp.setContentType(getFileContentType(fileName) + "; charset=" + charsetCode);
            // 设置响应内容的长度
            resp.setContentLength((int) file.length());
            // 输出
            outStream(new FileInputStream(file), resp.getOutputStream());
        } catch(Exception e) {
            log.error("执行文件下载时发生了异常====>{}", e.getMessage());
        }
    }

    /**========================================================*/
    /**
     * 根据文件名判断响应头部类型
     */
    private static String getFileContentType(String name) {
        String result = "";
        String fileType = name.toLowerCase();
        if(fileType.endsWith(".png")) {
            result = "image/png";
        } else if(fileType.endsWith(".gif")) {
            result = "image/gif";
        } else if(fileType.endsWith(".jpg") || fileType.endsWith(".jpeg")) {
            result = "image/jpeg";
        } else if(fileType.endsWith(".svg")) {
            result = "image/svg+xml";
        } else if(fileType.endsWith(".doc")) {
            result = "application/msword";
        } else if(fileType.endsWith(".xls")) {
            result = "application/x-excel";
        } else if(fileType.endsWith(".zip")) {
            result = "application/zip";
        } else if(fileType.endsWith(".pdf")) {
            result = "application/pdf";
        } else {
            result = "application/octet-stream";
        }
        return result;
    }

    /**
     * 基础字节数组输出
     */
    private static void outStream(InputStream is, OutputStream os) {
        try {
            byte[] buffer = new byte[10240];
            int length = -1;
            while((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } catch(Exception e) {
            log.error("执行 outStream 发生了异常===>{}", e.getMessage());
        } finally {
            try {
                os.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将File文件流转换为MultipartFile上传文件流
     *
     * @param file
     *
     * @return
     */
    public static MultipartFile getMulFileByFile(File file) {
        FileItem fileItem = createFileItem(file.getPath(), file.getName());
        return new CommonsMultipartFile(fileItem);
    }

    public static FileItem createFileItem(String filePath, String fileName) {
        String fieldName = "file";
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fieldName, "text/plain", false, fileName);
        File newFile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(newFile);
            OutputStream os = item.getOutputStream();
            while((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return item;
    }

    public void exportTxt(HttpServletResponse response, String text) {
        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition", "attachment;filename="
                                                  + genAttachmentFileName("文件名称", "JSON_FOR_UCC_")//设置名称格式，没有这个中文名称无法显示
                                                  + ".txt");
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(text.getBytes(StandardCharsets.UTF_8));
            buff.flush();
            buff.close();
        } catch(Exception e) {
            //LOGGER.error("导出文件文件出错:{}",e);
        } finally {
            try {
                assert buff != null;
                buff.close();
                outStr.close();
            } catch(Exception e) {
                //LOGGER.error("关闭流对象出错 e:{}",e);
            }
        }
    }

    private String genAttachmentFileName(String name, String code) {
        return name + code;
    }

}
