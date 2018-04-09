package com.rbac.rbacshow.sys.service;

import com.base.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "userauth", url = "http://localhost:5100")
public interface UserService {

  @RequestMapping(
    value = "/v1/auth/login",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> login(@RequestParam("s") String s);

  @RequestMapping(
    value = "/getToken",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> getToken(String s);
}
