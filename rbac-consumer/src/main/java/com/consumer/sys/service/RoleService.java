package com.consumer.sys.service;

import com.base.entity.QueryUserRole;
import com.base.entity.UserRole;
import com.consumer.common.base.service.GenericService;
import com.consumer.common.config.FullLogConfiguration;
import com.consumer.sys.fallback.RoleServiceHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
  value = "RBAC-PRODUCE",
  fallback = RoleServiceHystric.class,
  configuration = FullLogConfiguration.class,
  path = "/role"
)
public interface RoleService extends GenericService<UserRole, QueryUserRole> {

  @RequestMapping(
    value = "/loadRoleTree",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> loadRoleTree(UserRole userRole);
}
