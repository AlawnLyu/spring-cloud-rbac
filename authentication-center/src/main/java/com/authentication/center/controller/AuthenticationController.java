package com.authentication.center.controller;

import com.authentication.center.service.UserService;
import com.base.entity.Identify;
import com.base.entity.User;
import com.base.util.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/** 鉴权中心业务处理类controller */
@RestController
public class AuthenticationController {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  @Autowired private UserService userService;
  @Autowired private RedisCache redisCache;

  @RequestMapping(
    value = "/identify",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, Object> identify(@RequestBody Identify identify) {
    Map<String, Object> result = new HashMap<>();
    result.put("result", false);
    result.put("msg", "用户非法登陆");
    if (identify != null
        && identify.getToken() != null
        && !identify.getToken().equals("")
        && identify.getIp() != null
        && !identify.getIp().equals("")) {
      logger.info("鉴权中心收到的token的值是：" + identify.getIp() + "--" + identify.getToken());
      User user = redisCache.getObject(identify.getIp() + "-" + identify.getToken(), User.class);
      if (user == null) {
        result.put("result", false);
        result.put("msg", "用户未登陆，请重新登陆以后再操作！");
      } else {
        result.put("result", true);
        result.put("msg", "权限鉴定通过");
      }
    }
    return result;
  }

  @RequestMapping(
    value = "/login",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, Object> login(@RequestParam("s") String s) {
    Map<String, Object> result = new HashMap<>();
    return userService.findByLogin(s);
  }
}
