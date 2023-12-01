package com.zukxu.idem.advanced;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>
 * 签名请求包装类
 * </p>
 *
 * @author xupu
 * @since 2023/12/1 14:46:28
 */
public class SignRequestWrapper extends HttpServletRequestWrapper {
    //用于将流保存下来
    private byte[] requestBody;

    public SignRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = StreamUtils.copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() {
        // 创建一个 ByteArrayInputStream 对象，用于处理请求体
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);

        // 创建并返回一个 ServletInputStream 对象
        return new ServletInputStream() {
            @Override
            // 判断是否已经处理完毕
            public boolean isFinished() {
                return false;
            }

            @Override
            // 判断是否已经准备就绪可读
            public boolean isReady() {
                return false;
            }

            @Override
            // 设置读取监听器
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            // 读取一个字节的数据
            public int read() {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
