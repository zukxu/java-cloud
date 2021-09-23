package com.zukxu.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * redis监听控制
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/25 0025 15:27
 */
@Configuration
public class RedisConfig {
	/**
	 * 监听器
	 *
	 * @param connectionFactory 连接工厂
	 * @return 监听器
	 */
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		return container;
	}

	/**
	 * 自定义redisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);

		FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

		serializer.setObjectMapper(mapper);
		//配置类型转换器
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(serializer);

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
