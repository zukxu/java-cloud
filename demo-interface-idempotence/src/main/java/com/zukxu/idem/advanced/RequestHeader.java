package com.zukxu.idem.advanced;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestHeader {
    private String sign;//签名

    private Long timestamp;//时间戳

    private String nonce;//随机字符串【可以使用hash值】
}