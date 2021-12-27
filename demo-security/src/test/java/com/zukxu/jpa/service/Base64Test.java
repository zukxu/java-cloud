package com.zukxu.jpa.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-26 13:31
 */
@SpringBootTest
public class Base64Test {

    @Test
    void base64Dec() throws UnsupportedEncodingException {
        String rememberMe = "YWRtaW46MTYzMzg0MzM5OTM3MTo1NWRiOGI5OWY1OWM1NjhjMzNmOGNmNDdiMTc1YjU4MQ";
        String s = new String(Base64.getDecoder().decode(rememberMe), StandardCharsets.UTF_8);
        String[] strings = s.split(":");
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(Long.parseLong(strings[1]), 0, ZoneOffset.UTC);
        System.out.println(localDateTime);
        System.out.println("s = " + s);
    }
}
