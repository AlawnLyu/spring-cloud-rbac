package com.produce.sys.controller;

import com.base.entity.Dict;
import com.base.entity.QueryDict;
import com.produce.common.base.constant.SystemStaticConst;
import com.produce.common.base.controller.GenericController;
import com.produce.common.base.service.GenericService;
import com.produce.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dict")
public class DictController extends GenericController<Dict, QueryDict> {

    @Autowired
    private DictService dictService;

    @Override
    protected GenericService<Dict, QueryDict> getService() {
        return dictService;
    }

    /**
     * 将字典数据初始化到前端js中
     *
     * @return
     */
    @RequestMapping(value = "/loadDict", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> loadDict() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Dict> dictList = dictService.query(new QueryDict("1"));
        resultMap.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        resultMap.put("data", dictList);
        return resultMap;
    }
}
