package com.consumer.sys.fallback;

import com.base.entity.QueryUserRole;
import com.base.entity.UserRole;
import com.consumer.common.base.hystrix.GenericHystric;
import com.consumer.sys.service.RoleService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RoleServiceHystric extends GenericHystric<UserRole, QueryUserRole>
    implements RoleService {
  @Override
  public Map<String, Object> loadRoleTree(UserRole userRole) {
    return getErrorMap();
  }
}
