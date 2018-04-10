package com.base.entity;

import com.base.common.QueryBase;

public class QueryTree extends QueryBase {

  private String code;
  private String icon;
  private String name;
  private long pId;
  private long treeOrder;
  private String url;
  private String state;
  private int display;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getpId() {
    return pId;
  }

  public void setpId(long pId) {
    this.pId = pId;
  }

  public long getTreeOrder() {
    return treeOrder;
  }

  public void setTreeOrder(long treeOrder) {
    this.treeOrder = treeOrder;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public int getDisplay() {
    return display;
  }

  public void setDisplay(int display) {
    this.display = display;
  }
}
