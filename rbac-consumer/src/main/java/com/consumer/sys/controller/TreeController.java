package com.consumer.sys.controller;

import com.base.entity.QueryTree;
import com.base.entity.Tree;
import com.consumer.common.base.controller.GenericController;
import com.consumer.common.base.service.GenericService;
import com.consumer.sys.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tree")
public class TreeController extends GenericController<Tree, QueryTree> {

  @Autowired private TreeService treeService;

  @Override
  protected GenericService<Tree, QueryTree> getService() {
    return treeService;
  }

  @RequestMapping(
    value = "/mainTree",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, Object> mainTree(@RequestHeader("token") String token) {
    return treeService.mainTree(token);
  }

  @RequestMapping(
    value = "/loadUserTree",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Map<String, Object> loadUserTree() {
    return treeService.loadUserTree();
  }
}
