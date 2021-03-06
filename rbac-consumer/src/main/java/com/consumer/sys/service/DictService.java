package com.consumer.sys.service;

import com.base.entity.Dict;
import com.base.entity.QueryDict;
import com.consumer.common.base.service.GenericService;
import com.consumer.common.config.FullLogConfiguration;
import com.consumer.sys.fallback.DictServiceHystric;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(
  value = "RBAC-PRODUCE",
  fallback = DictServiceHystric.class,
  configuration = FullLogConfiguration.class,
  path = "/dict"
)
public interface DictService extends GenericService<Dict, QueryDict> {

    /**
     * 功能描述：将字典数据初始化到前端js中
     *
     * @return
     */
    @RequestMapping(value = "/loadDict", method = RequestMethod.POST)
    public Map<String, Object> loadDict();
}
