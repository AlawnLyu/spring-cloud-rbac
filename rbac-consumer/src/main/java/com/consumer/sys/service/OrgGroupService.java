package com.consumer.sys.service;

import com.base.entity.OrgGroup;
import com.base.entity.QueryOrgGroup;
import com.base.entity.QueryUser;
import com.consumer.common.base.service.GenericService;
import com.consumer.common.config.FullLogConfiguration;
import com.consumer.sys.fallback.OrgGroupServiceHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
  value = "RBAC-PRODUCE",
  fallback = OrgGroupServiceHystric.class,
  configuration = FullLogConfiguration.class,
  path = "/group"
)
public interface OrgGroupService extends GenericService<OrgGroup, QueryOrgGroup> {

  /**
   * 获取组织架构底下的相应的用户
   *
   * @param queryUser
   * @return
   */
  @RequestMapping(
    value = "/userList",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> userList(@RequestBody QueryUser queryUser);

  /**
   * 获取组织架构的整颗菜单树
   *
   * @return
   */
  @RequestMapping(
    value = "/loadGroupTree",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  Map<String, Object> loadGroupTree();
}
