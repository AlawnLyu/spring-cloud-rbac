package com.consumer.sys.fallback;

import com.base.entity.QueryTree;
import com.base.entity.Tree;
import com.base.entity.User;
import com.consumer.common.base.hystrix.GenericHystric;
import com.consumer.sys.service.TreeService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TreeServiceHystric extends GenericHystric<Tree, QueryTree> implements TreeService {
  @Override
  public Map<String, Object> mainTree(String token) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> getTreeByUser(User user) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> loadUserTree() {
    return getErrorMap();
  }
}
