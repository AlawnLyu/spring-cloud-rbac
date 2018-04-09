package com.rbac.rbacshow.common.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class LoginSuccessHandle implements AuthenticationSuccessHandler {
  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Authentication authentication)
      throws IOException, ServletException {
    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
    String path = httpServletRequest.getContextPath();
    String basePath =
        httpServletRequest.getScheme()
            + "://"
            + httpServletRequest.getServerName()
            + ":"
            + httpServletRequest.getServerPort()
            + path
            + "/";
    httpServletResponse.sendRedirect(basePath + "main");
  }
}
