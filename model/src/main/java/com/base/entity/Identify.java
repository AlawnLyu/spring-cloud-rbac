package com.base.entity;

public class Identify {

  public Identify() {
    super();
  }

  public Identify(String token, String ip, String uri) {
    this.token = token;
    this.ip = ip;
    this.uri = uri;
  }

  /** 登陆token */
  private String token;
  /** 当前用户登陆的IP */
  private String ip;
  /** 用户请求uir */
  private String uri;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
