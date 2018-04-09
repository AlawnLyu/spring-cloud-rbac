package com.rbac.rbacshow.common.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.rbac.rbacshow.*.dao")
public class MybatisConfig {
}
