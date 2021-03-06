package com.authentication.center.service;

import com.authentication.center.config.FullLogConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "RBAC-CONSUMER", configuration = FullLogConfiguration.class, path = "/user")
public interface UserService {

  @RequestMapping(
    value = "/findByLogin",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> findByLogin(@RequestParam("s") String s);
}
