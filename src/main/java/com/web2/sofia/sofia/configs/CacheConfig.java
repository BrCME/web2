package com.web2.sofia.sofia.configs;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConversionServiceFactory;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class CacheConfig {
	@Bean
	RedisConnectionFactory cacheConnectionFactory() {
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
	}

	@Bean
	RedisCacheManager cacheManager(RedisConnectionFactory cacheConnectionFactory) {
		return RedisCacheManager
				.builder(cacheConnectionFactory)
				.cacheDefaults(cacheConfiguration())
				.build();
	}

	// @Bean
	// RedisTemplate<String, String> redisTemplate(RedisConnectionFactory
	// cacheConnectionFactory) {
	// RedisTemplate<String, String> template = new RedisTemplate<>();

	// template.setConnectionFactory(cacheConnectionFactory);

	// return template;
	// }

	@Bean
	RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(5))
				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.disableCachingNullValues();
	}
}
