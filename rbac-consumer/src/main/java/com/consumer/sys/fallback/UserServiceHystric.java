package com.consumer.sys.fallback;

import com.base.entity.QueryUser;
import com.base.entity.User;
import com.consumer.common.base.hystrix.GenericHystric;
import com.consumer.sys.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserServiceHystric extends GenericHystric<User, QueryUser> implements UserService {
  @Override
  public Map<String, Object> userControl(User entity) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> loadRoles() {
    return getErrorMap();
  }
}
