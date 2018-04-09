package com.authentication.center;

import com.base.util.redis.RedisCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AuthenticationCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationCenterApplication.class, args);
    }

    @Bean
    public RedisCache redisCache() {
        return new RedisCache();
    }
}
