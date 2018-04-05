package com.produce.common.config.swagger;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private Environment env;

    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler requestHandler) {
                // 除非是在开发环境中否则不开启swagger2
                String active = env.getProperty("spring.profiles.active");
                if (!active.equalsIgnoreCase("prod-8100")) {
                    return false;
                }
                Class<?> declaringClass = requestHandler.declaringClass();
                if (declaringClass == BasicErrorController.class) {
                    return false;
                }
                if (declaringClass.isAnnotationPresent(RestController.class)) {
                    return true;
                }
                if (requestHandler.isAnnotatedWith(ResponseBody.class)) {
                    return true;
                }
                return false;
            }
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger2 接口测试专用页面！")
                .version("1.0")
                .build();
    }
}
