package com.consumer.sys.fallback;

import com.base.entity.OrgGroup;
import com.base.entity.QueryOrgGroup;
import com.base.entity.QueryUser;
import com.consumer.common.base.hystrix.GenericHystric;
import com.consumer.sys.service.OrgGroupService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrgGroupServiceHystric extends GenericHystric<OrgGroup, QueryOrgGroup>
    implements OrgGroupService {

  @Override
  public Map<String, Object> userList(QueryUser queryUser) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> loadGroupTree() {
    return getErrorMap();
  }
}
