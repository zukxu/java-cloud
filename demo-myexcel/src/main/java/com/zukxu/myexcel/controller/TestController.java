package com.zukxu.myexcel.controller;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-01-26 15:14
 */
public class TestController {

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\17747\\Downloads\\ceshi\\report\\sn\\5G1期.xlsx";
        File fi = new File(path);
        if (!fi.getParentFile().exists()) {
            fi.getParentFile().mkdir();
        }
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fi));

        String filePath = "C:\\Users\\17747\\Downloads\\ceshi\\report\\sn\\5G1期.xlsx";
        File file = new File(filePath);
        FileItem fileItem = getMultipartFile(file, "templFileItem");
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        List<String[]> list = ExcelUtil.readExcel(multipartFile);
        StringBuffer buff = new StringBuffer();
        buff.append("地市").append("|").append("区县").append("|").append("指标").append("|").append("指标值").append("\n");
        osw.write(buff.toString());
        String[] head = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            String[] rowArr = list.get(i);
            buff.setLength(0);
            if (StrUtil.isBlank(rowArr[0])) continue;
            String row1 = rowArr[0] + "|" + rowArr[1];
            for (int j = 2; j < rowArr.length; j++) {
                buff.append(row1).append("|").append(head[j]).append("|").append(rowArr[j]).append("\n");
                System.out.print(buff);
                osw.write(buff.toString());
                buff.setLength(0);
            }
        }
        osw.close();

    }

    /**
     * 将file转换成fileItem
     *
     * @param file
     * @param fieldName
     * @return
     */
    public static FileItem getMultipartFile(File file, String fieldName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(fieldName, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}




