package com.rbac.rbacshow.common.config.security;

import com.base.entity.User;
import com.base.util.ip.IPUtil;
import com.base.util.redis.RedisCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbac.rbacshow.common.util.user.UserInfoUtil;
import com.rbac.rbacshow.sys.mapper.UserMapper;
import com.rbac.rbacshow.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.Map;

public class CustomUserService implements UserDetailsService {

  @Autowired private UserService userService;
  @Autowired private RedisCache redisCache;
  @Autowired private UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Map<String, Object> result = userService.login(s);
    if ((boolean) result.get("result") == false) {
      throw new RuntimeException("" + result.get("msg"));
    }
    User user = new ObjectMapper().convertValue(result.get("data"), User.class);
    if (user == null) {
      throw new UsernameNotFoundException("用户名不存在!");
    }
//    Map<String, Object> tokenMap = userService.getToken(s);
//    if ((boolean) result.get("result") == false) {
//      throw new RuntimeException("" + result.get("msg"));
//    }
//    String token = (String) result.get("data");
    String token = "abcdef";
    if (token == null) {
      throw new UsernameNotFoundException("获取Token失败!");
    }
//    redisCache.setObject(token, user);
//    redisCache.expire(token, 3600);
//    UserInfoUtil.getRequest().getSession().setAttribute("token", token);
//    String IP = IPUtil.getIpAddress(UserInfoUtil.getRequest());
//    redisCache.setObject(IP + "-" + token, user);
//    redisCache.expire(IP + "-" + token, 3600);
    user.setLastLoginDate(new Date());
    if (user.getState().equalsIgnoreCase("0")) {
      throw new LockedException("用户账号被冻结，无法登陆，请联系管理员！");
    }
    return userMapper.baseToUser(user);
  }
}
