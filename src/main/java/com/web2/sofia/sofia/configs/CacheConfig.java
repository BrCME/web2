package com.web2.sofia.sofia.configs;

import java.time.Duration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration("server", 6379));
	}

	@Bean
	RedisCacheManager cacheManager(RedisConnectionFactory cacheConnectionFactory) {
		return RedisCacheManager.create(cacheConnectionFactory);
	}

	@Bean
	RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(5))
				.disableCachingNullValues()
				.serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}

	@Bean
	RedisCacheManagerBuilderCustomizer cacheManagerBuilderCustomizer() {
		return builder -> builder
			.withCacheConfiguration("fazendinha", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(2)))
			.build();
	}
}
