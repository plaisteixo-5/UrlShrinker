package com.learning.UrlShrinker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class UrlShrinkerApplication {
	
	@Bean
	public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<?, ?> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		
		return template;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(UrlShrinkerApplication.class, args);
	}

}
