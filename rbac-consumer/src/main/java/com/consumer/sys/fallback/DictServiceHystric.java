package com.consumer.sys.fallback;

import com.base.entity.Dict;
import com.base.entity.QueryDict;
import com.consumer.common.base.constant.SystemStaticConst;
import com.consumer.common.base.hystrix.GenericHystric;
import com.consumer.sys.service.DictService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DictServiceHystric extends GenericHystric<Dict, QueryDict> implements DictService {
  @Override
  public Map<String, Object> loadDict() {
    return getErrorMap();
  }
}
