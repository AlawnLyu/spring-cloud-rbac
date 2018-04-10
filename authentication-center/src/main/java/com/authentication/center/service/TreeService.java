package com.authentication.center.service;

import com.authentication.center.config.FullLogConfiguration;
import com.base.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "RBAC-CONSUMER", configuration = FullLogConfiguration.class, path = "/tree")
public interface TreeService {

  @RequestMapping(
    value = "/getTreeByUser",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> getTreeByUser(@RequestBody User user);
}
