package com.zukxu.alipay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 配置类
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/12 0012 15:47
 */
@Data
@Component
@PropertySource("classpath:/config/alipay.properties")
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
	private String appPrivateKey;
	private String alipayPublicKey;
	private String appId;
	private String charset;
	private String format;
	private String signType;
	private String prodCode;
	private String gatewayUrl;
	private String notifyUrl;
	private String returnUrl;
}
