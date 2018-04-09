package com.consumer.sys.service;

import com.base.entity.QueryUser;
import com.base.entity.User;
import com.consumer.common.base.service.GenericService;
import com.consumer.common.config.FullLogConfiguration;
import com.consumer.sys.fallback.UserServiceHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
  value = "RBAC-PRODUCE",
  fallback = UserServiceHystric.class,
  configuration = FullLogConfiguration.class,
  path = "/user"
)
public interface UserService extends GenericService<User, QueryUser> {

  /**
   * 更新用户状态为禁用/启用
   *
   * @param entity
   * @return
   */
  @RequestMapping(
    value = "/userControl",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> userControl(@RequestBody User entity);

  /**
   * 加载所有的权限数据
   *
   * @return
   */
  @RequestMapping(
    value = "/loadRoles",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> loadRoles();
}
