package com.consumer.sys.fallback;

import com.base.entity.Dict;
import com.base.entity.QueryDict;
import com.consumer.common.base.constant.SystemStaticConst;
import com.consumer.sys.service.DictService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DictServiceHystric implements DictService {
    @Override
    public Map<String, Object> loadDict() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
        resultMap.put(SystemStaticConst.MSG, "服务发生错误");
        return resultMap;
    }

    @Override
    public Map<String, Object> getById(int id) {
        return null;
    }

    @Override
    public Map<String, Object> get(Dict entity) {
        return null;
    }

    @Override
    public Map<String, Object> save(Dict entity) {
        return null;
    }

    @Override
    public Map<String, Object> update(Dict entity) {
        return null;
    }

    @Override
    public Map<String, Object> remove(Dict entity) {
        return null;
    }

    @Override
    public Map<String, Object> removeBath(String json) {
        return null;
    }

    @Override
    public Map<String, Object> list(QueryDict entity) {
        return null;
    }

    @Override
    public Map<String, Object> isExist(QueryDict entity) {
        return null;
    }
}
