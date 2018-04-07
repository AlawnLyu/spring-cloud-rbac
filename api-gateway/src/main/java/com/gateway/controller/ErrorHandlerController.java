package com.gateway.controller;

import com.gateway.util.CombineException;
import com.netflix.zuul.context.RequestContext;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorHandlerController implements ErrorController {
  @Override
  public String getErrorPath() {
    return "/error";
  }

  @RequestMapping(value = "/error")
  public String error(HttpServletRequest request) {
    RequestContext ctx = RequestContext.getCurrentContext();
    return CombineException.getErrorException(ctx.getThrowable()).toString();
  }
}
