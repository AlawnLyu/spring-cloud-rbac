package com.authentication.center.controller;

import com.authentication.center.config.JwtProperties;
import com.authentication.center.service.TreeService;
import com.authentication.center.service.UserService;
import com.base.dto.AccessToken;
import com.base.entity.Identify;
import com.base.entity.Tree;
import com.base.entity.User;
import com.base.entity.UserRole;
import com.base.util.json.JsonHelper;
import com.base.util.jwt.JWTUtil;
import com.base.util.redis.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 鉴权中心业务处理类controller */
@RestController
public class AuthenticationController {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  @Autowired private UserService userService;
  @Autowired private TreeService treeService;
  @Autowired private RedisCache redisCache;
  @Autowired private JwtProperties jwtProperties;

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
        && !identify.getIp().equals("")
        && identify.getUri() != null
        && !identify.getUri().equals("")) {
      logger.info("鉴权中心收到的token的值是：{}", identify.getToken());
      User user = redisCache.getObject(identify.getIp() + "-" + identify.getToken(), User.class);
      if (user == null) {
        result.put("result", false);
        result.put("msg", "用户未登陆，请重新登陆以后再操作！");
      } else {
        List<Tree> trees = redisCache.getList("tree-" + identify.getToken(), Tree.class);
        boolean hasPermission = false;
        if (trees != null) {
          for (Tree tree : trees) {
            if (identify.getUri().contains(tree.getUrl())) {
              hasPermission = true;
              break;
            }
          }
        }
        if (hasPermission) {
          result.put("result", true);
          result.put("msg", "权限鉴定通过");
        } else {
          result.put("result", false);
          result.put("msg", "权限鉴定失败");
        }
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
    return userService.findByLogin(s);
  }

  @RequestMapping(
    value = "/getToken",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, Object> getToken(@RequestParam("s") String s) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> userMap = userService.findByLogin(s);
    if ((boolean) userMap.get("result") == false) {
      result.put("result", false);
      result.put("msg", "查询用户角色失败！");
      return result;
    }
    User user = JsonHelper.newObjectMapperInstance().convertValue(userMap.get("data"), User.class);
    String roleArray = "";
    for (UserRole role : user.getRoles()) {
      if (role.getName() != null) {
        roleArray += role.getName() + ",";
      }
    }
    logger.info("用户{}的角色是：{}", user.getLogin(), roleArray);
    roleArray = roleArray.substring(0, roleArray.length() - 1);
    logger.info("用户{}的角色是：{}", user.getLogin(), roleArray);
    String token =
        JWTUtil.createJWT(
            user.getLogin(),
            user.getId() + "",
            roleArray,
            jwtProperties.getIssuer(),
            jwtProperties.getExpiration(),
            jwtProperties.getSecret());
    logger.info("生成的Token是：{}", token);
    Map<String, Object> treeMap = treeService.getTreeByUser(user);
    if ((boolean) treeMap.get("result") == false) {
      result.put("result", false);
      result.put("msg", "查询用户角色失败！");
      return result;
    }
    List<Tree> trees = (ArrayList) treeMap.get("data");
    redisCache.setList("tree-" + token, trees);
    redisCache.expire("tree-" + token, jwtProperties.getExpiration());
    AccessToken accessToken = new AccessToken();
    accessToken.setAccess_token(token);
    accessToken.setExpires_in(jwtProperties.getExpiration());
    accessToken.setToken_type("bearer");
    result.put("result", true);
    result.put("data", accessToken);
    return result;
  }
}
