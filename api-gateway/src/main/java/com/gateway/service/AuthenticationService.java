package com.gateway.service;

import com.base.entity.Identify;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "AUTHENTICATION-SERVICE")
public interface AuthenticationService {

    /**
     * 调用生产者端的轨迹处理方法
     *
     * @param identify
     * @return
     */
    @RequestMapping(value = "/identify", method = RequestMethod.POST)
    Map<String, Object> identify(@RequestBody Identify identify);
}
