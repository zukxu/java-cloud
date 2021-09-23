package com.zukxu.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author zukxu
 * CreateTime: 2020/10/31 0031 15:12
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
	/**
	 * minio 服务地址 http://ip:port
	 */
	private String endpoint;
	/**
	 * 用户名
	 */
	private String accessKey;
	/**
	 * 密码
	 */
	private String secretKey;
	/**
	 * 桶名称
	 */
	private String bucketName;
}