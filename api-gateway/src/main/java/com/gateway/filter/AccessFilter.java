package com.gateway.filter;

import com.base.entity.Identify;
import com.base.util.ip.IPUtil;
import com.gateway.service.AuthenticationService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AccessFilter extends ZuulFilter {

  private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

  @Autowired public SimpleRouteLocator simpleRouteLocator;

  @Autowired private AuthenticationService authenticationService;

  /**
   * 过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。这里定义为pre, 代表会在请求被路由之前执行
   *
   * @return
   */
  @Override
  public String filterType() {
    return "pre";
  }

  /**
   * 过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行
   *
   * @return
   */
  @Override
  public int filterOrder() {
    return 0;
  }

  /**
   * 判断该过滤器是否需要被执行。这里我们直接返回了true, 因此该过滤器对所有请求都会生效。实际运用中我们可以利用该函数来指定过滤器的有效范围
   *
   * @return
   */
  @Override
  public boolean shouldFilter() {
    try {
      RequestContext requestContext = getCurrentContext();
      HttpServletRequest request = requestContext.getRequest();

      String actualPath = getUri(request.getRequestURI());
      String targetLocation = getTargetLocation(request.getRequestURI());

      if (logger.isDebugEnabled()) {
        logger.debug("request.getRequestURI: " + request.getRequestURI());
        logger.debug("request.getServletPath: " + request.getServletPath());
        logger.debug("request.getRequestURL: " + request.getRequestURL());
        logger.debug("actualPath: " + actualPath);
        logger.debug("targetLocation: " + targetLocation);
      }

      if (targetLocation.equals("authentication-service")
          && (actualPath.equals("/login") || actualPath.equals("/getToken"))) {
        return false;
      }
      return true;
    } catch (Exception e) {
      // e.printStackTrace();
      return true;
    }
  }

  /**
   * 这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，不对其进行路由，然后通过
   * ctx.setResponseStatusCode(401)设置了其返回的错误码，当然也可以进一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回的body内容进行编辑等
   *
   * @return
   */
  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    HttpServletResponse response = ctx.getResponse();

    response.setHeader("Access-Control-Allow-Headers", "Authentication");
    response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

    logger.info(
        "send {} request to {}",
        request.getMethod(),
        request.getRequestURL().toString() + "--" + request.getContentType());

    String authorizationHeader = ctx.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
    String token = authorizationHeader.substring("Bearer".length()).trim();

    if (token == null) {
      logger.warn("access token is empty");
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(401);
      ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      ctx.set("error.exception", new RuntimeException("AccessToken不允许为空！"));
      throw new RuntimeException("AccessToken不允许为空！");
    }

    Map<String, Object> result =
        authenticationService.identify(
            new Identify(
                token, IPUtil.getIpAddress(request), getUri(ctx.getRequest().getRequestURI())));

    logger.info("鉴权中心鉴定结果是：{}", result.get("msg"));

    if ((boolean) result.get("result") == false) {
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(401);
      ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      ctx.set("error.exception", new RuntimeException((String) result.get("msg")));
      throw new RuntimeException((String) result.get("msg"));
    }
    return null;
  }

  private RequestContext getCurrentContext() {
    return RequestContext.getCurrentContext();
  }

  private String getUri(String requestUri) {
    Route route = simpleRouteLocator.getMatchingRoute(requestUri);
    return route.getPath();
  }

  private String getTargetLocation(String requestUri) {
    Route route = simpleRouteLocator.getMatchingRoute(requestUri);
    return route.getLocation();
  }
}
