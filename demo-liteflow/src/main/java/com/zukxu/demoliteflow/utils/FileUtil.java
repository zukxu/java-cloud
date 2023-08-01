package com.zukxu.demoliteflow.utils;

import cn.hutool.core.util.RandomUtil;
import com.zukxu.common.result.R;
import com.zukxu.demoliteflow.constant.CSVC;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Vector;

public class FileUtil {

    //@formatter:off
    public static final String JT_UPLOAD_DIR = "/incoming/csvc/";
    public static final String JT_DOWNLOAD_DIR = "/outgoing/csvc/";
    public static final String SN_UPLOAD_DIR = "/app/bonc/incoming/csvc/";
    public static final String SN_DOWNLOAD_DIR = "/app/bonc/outgoing/csvc/";
    final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static int num = 1;
    //@formatter:on

    /**
     * 创建随机新名称
     *
     * @param fileName 文件名
     * @return str
     */
    @SneakyThrows
    public static String getRandomFileName(String fileName) {
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        if (num > 999) {
            num = 1;
        }
        if (num < 10) {
            suffixName = "_00" + num + suffixName;
        } else if (num < 100) {
            suffixName = "_0" + num + suffixName;
        }
        num++;
        return CSVC.WORK_FILE_PREFIX + WFUtil.dateFormat(LocalDateTime.now()) + suffixName;
    }

    /**
     * 获取档案编号
     */
    @SneakyThrows
    public static String getFileNo(String fileName) {
        //取值说明：省公司发起：YYYYMMDD＋FNO＋3位省代码＋8位流水号
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        return WFUtil.dateFormat(LocalDateTime.now()) + "FNO" + CSVC.CODE_SN + RandomUtil.randomNumbers(8) + suffixName;
    }

    public static String getFileNo() {
        //取值说明：省公司发起：YYYYMMDD＋FNO＋3位省代码＋8位流水号
        //平台发起：YYYYMMDD＋ FNO＋4位系统编码＋7位流水号
        return WFUtil.dateFormat(LocalDateTime.now()) + "FNO" + CSVC.CODE_SN + RandomUtil.randomNumbers(8);
    }

    /**
     * 获取文件绝对路径
     *
     * @param uploadDir upd
     * @param fileName  fileName
     * @return File
     */
    public static File getAbsoluteFile(String uploadDir, String fileName) {
        File desc = new File(uploadDir, fileName);
        logger.info("文件是否存在：{}", desc.exists());
        logger.info("文件大小：{}", cn.hutool.core.io.FileUtil.readableFileSize(desc));
        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc.isAbsolute() ? desc : desc.getAbsoluteFile();
    }

    /** ======================================================== */
    public static R<?> download(String path) throws Exception {
        logger.info("Download::::>");
        /*if(path == null || "".equals(path)) {
            return R.fail("没有附件");
        }
        String[] p = null;
        if(path.contains("|")) {
            p = path.split("\\|");
        }

        Map<String, String> sftpDetails = SFTPConstants.sftpDetails();
        SFTPChannel channel = new SFTPChannel();
        ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
        if(p != null) {
            //多个附件下载
            for(String s : p) {
                downSftpFile(chSftp, s, JT_DOWNLOAD_DIR, path, SN_DOWNLOAD_DIR);
            }
        } else {
            downSftpFile(chSftp, path, JT_DOWNLOAD_DIR, path, SN_DOWNLOAD_DIR);
        }*/
        return R.ok();
    }

    /**
     * 下载sftp文件
     *
     * @param sftp        sftp
     * @param newFileName 新文件名称
     * @param path        文件路径
     * @param fileName    文件名称
     * @param downUrl     下载到本地的路径
     */
    /*public static void downSftpFile(ChannelSftp sftp, String newFileName, String path, String fileName, String downUrl) {
        OutputStream os;
        try {
            File localFile = new File(downUrl, newFileName);
            logger.info("localFile====>{}", downUrl + newFileName);
            if(!localFile.getParentFile().exists()) {
                localFile.getParentFile().getParentFile().mkdir();
                localFile.getParentFile().mkdirs();
                localFile.createNewFile();
            }

            if(path != null && !"".equals(path)) {
                sftp.cd(path);//进入所在路径
            }
            os = new FileOutputStream(localFile);
            //sftp上传文件
            sftp.get(path + fileName, os);
            os.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 扫描远程附件文件
     *
     * @param fileNamePrefix 文件名前缀
     */
    public static void lsSftpFileList(String fileNamePrefix) {
       /* throws JSchException, SftpException {
        Map<String, String> sftpDetails = SFTPConstants.sftpDetails();
        SFTPChannel channel = new SFTPChannel();
        ChannelSftp channelSftp = channel.getChannel(sftpDetails, 60000);
        fileNamePrefix = fileNamePrefix + "*";
        channelSftp.cd(JT_DOWNLOAD_DIR);
        Vector<ChannelSftp.LsEntry> list = channelSftp.ls(fileNamePrefix);
        for(ChannelSftp.LsEntry entry : list) {
            logger.info(entry.getFilename());
        }*/
    }

    /**
     * 下载文件
     *
     * @param path     文件的位置
     * @param fileName 自定义下载文件的名称
     * @param resp     http响应
     * @param req      http请求
     */
    public static void downloadFile(String path, String fileName, HttpServletRequest req, HttpServletResponse resp) {
        try {
            File file = new File(path, fileName);
            //中文乱码解决
            String type = req.getHeader("User-Agent").toLowerCase();
            // 字符编码格式
            if (type.indexOf("firefox") > 0 || type.indexOf("chrome") > 0) {
                //浏览器 谷歌或火狐
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            } else {
                //IE
                fileName = URLEncoder.encode(fileName, String.valueOf(StandardCharsets.UTF_8));
            }
            initResp(resp, fileName);
            resp.setContentLength((int) file.length());
            outStream(Files.newInputStream(file.toPath()), resp.getOutputStream());
        } catch (Exception e) {
            logger.error("执行downloadFile发生了异常：{}", e.getMessage());
        }
    }

    private static void initResp(HttpServletResponse resp, String fileName) {
        resp.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
        resp.setContentType(getFileContentType(fileName) + "; charset=" + StandardCharsets.UTF_8);
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
    }

    public static void downloadData(HttpServletResponse resp, String data, String fileName) {
        initResp(resp, new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        resp.setContentLength(data.length());
        try (BufferedOutputStream buff = new BufferedOutputStream(resp.getOutputStream())) {
            buff.write(data.getBytes(StandardCharsets.UTF_8));
            buff.flush();
        } catch (Exception e) {
            logger.error("导出文件文件出错:{}", e.getMessage());
        }

    }

    /**
     * 文件的内容类型
     */
    private static String getFileContentType(String name) {
        String result = "";
        String fileType = name.toLowerCase();
        if (fileType.endsWith(".png")) {
            result = "image/png";
        } else if (fileType.endsWith(".gif")) {
            result = "image/gif";
        } else if (fileType.endsWith(".jpg") || fileType.endsWith(".jpeg")) {
            result = "image/jpeg";
        } else if (fileType.endsWith(".svg")) {
            result = "image/svg+xml";
        } else if (fileType.endsWith(".doc")) {
            result = "application/msword";
        } else if (fileType.endsWith(".xls")) {
            result = "application/x-excel";
        } else if (fileType.endsWith(".zip")) {
            result = "application/zip";
        } else if (fileType.endsWith(".pdf")) {
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
            while ((length = is.read(buffer)) != -1) {
                os.write(buffer, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            logger.error("执行 outStream 发生了异常：{}", e.getMessage());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将File文件流转换为MultipartFile上传文件流
     *
     * @param file file
     *
     * @return MultipartFile
     */
    /*public static MultipartFile getMulFileByFile(File file) {
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
    }*/

}
