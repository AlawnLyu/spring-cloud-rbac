package com.consumer.sys.controller;

import com.base.entity.QueryUser;
import com.base.entity.User;
import com.consumer.common.base.controller.GenericController;
import com.consumer.common.base.service.GenericService;
import com.consumer.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends GenericController<User, QueryUser> {

  @Autowired private UserService userService;

  @Override
  protected GenericService<User, QueryUser> getService() {
    return userService;
  }

  @RequestMapping(
    value = "/userControl",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, Object> userControl(User entity) {
    return userService.userControl(entity);
  }

  @RequestMapping(
    value = "/loadRoles",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, Object> loadRoles() {
    return userService.loadRoles();
  }
}
