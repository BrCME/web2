package com.web2.safia.shared.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import tools.jackson.databind.ObjectMapper;

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

	@Bean
	RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(5))
				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJacksonJsonRedisSerializer(new ObjectMapper())))
				.disableCachingNullValues();
	}
}
