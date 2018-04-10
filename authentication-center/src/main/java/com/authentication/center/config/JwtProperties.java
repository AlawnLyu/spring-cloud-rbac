package com.authentication.center.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("jwt.token")
public class JwtProperties {

  private String secret;
  private Long expiration;
  private String issuer;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Long getExpiration() {
    return expiration;
  }

  public void setExpiration(Long expiration) {
    this.expiration = expiration;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }
}
