package com.base.entity;

public class Identify {

    public Identify() {
        super();
    }

    public Identify(String token, String ip) {
        this.token = token;
        this.ip = ip;
    }

    /**
     * 登陆token
     */
    private String token;
    /**
     * 当前用户登陆的IP
     */
    private String ip;

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
}
