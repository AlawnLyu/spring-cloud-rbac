package com.rbac.rbacshow;

import com.base.util.redis.RedisCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class RbacShowApplication {

  public static void main(String[] args) {
    SpringApplication.run(RbacShowApplication.class, args);
  }

  @Bean
  public RedisCache redisCache() {
    return new RedisCache();
  }
}
