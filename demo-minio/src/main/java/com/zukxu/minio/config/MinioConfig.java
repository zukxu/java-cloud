package com.zukxu.minio.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Minio配置类
 * </p>
 *
 * @author zukxu
 * CreateTime: 2020/12/22 0022 17:34
 */
@Configuration
public class MinioConfig {
	@Autowired
	private MinioProperties minio;

	@Bean
	public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
		return new MinioClient(minio.getEndpoint(), minio.getAccessKey(), minio.getSecretKey());
	}
}
