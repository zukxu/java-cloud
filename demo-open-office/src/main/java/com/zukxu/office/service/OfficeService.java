package com.zukxu.office.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zukxu.office.util.FileConvertUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 业务代码
 *
 * @author xupu
 * @Date 2021-11-05 10:45
 */
@Service
public class OfficeService {

    public void onlinePreview(String url, HttpServletResponse response) throws Exception {
        //获取文件类型
        List<String> str = StrUtil.split(url, "\\.");

        if (CollectionUtil.isEmpty(str)) {
            throw new Exception("文件格式不正确");
        }
        String suffix = str.get(str.size() - 1);
        if (!suffix.equals("txt") && !suffix.equals("doc") && !suffix.equals("docx") && !suffix.equals("xls")
                && !suffix.equals("xlsx") && !suffix.equals("ppt") && !suffix.equals("pptx")) {
            throw new Exception("文件格式不支持预览");
        }
        InputStream in = FileConvertUtil.convertNetFile(url, suffix);
        OutputStream outputStream = response.getOutputStream();
        //创建存放文件内容的数组
        byte[] buff = new byte[1024];
        //所读取的内容使用n来接收
        int n;
        //当没有读取完时,继续读取,循环
        while ((n = in.read(buff)) != -1) {
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff, 0, n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
        in.close();
    }

}
