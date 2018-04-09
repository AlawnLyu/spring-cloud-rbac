package com.consumer.common.base.hystrix;

import com.base.common.QueryBase;
import com.consumer.common.base.constant.SystemStaticConst;
import com.consumer.common.base.service.GenericService;

import java.util.HashMap;
import java.util.Map;

public class GenericHystric<T, Q extends QueryBase> implements GenericService<T, Q> {

  protected Map<String, Object> getErrorMap() {
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
    resultMap.put(SystemStaticConst.MSG, "获取数据失败！");
    return resultMap;
  }

  @Override
  public Map<String, Object> getById(int id) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> get(T entity) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> save(T entity) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> update(T entity) {
    return null;
  }

  @Override
  public Map<String, Object> remove(T entity) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> removeBath(String json) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> list(Q entity) {
    return getErrorMap();
  }

  @Override
  public Map<String, Object> isExist(Q entity) {
    return getErrorMap();
  }
}
