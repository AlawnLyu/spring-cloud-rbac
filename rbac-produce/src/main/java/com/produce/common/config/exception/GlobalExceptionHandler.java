package com.produce.common.config.exception;

import com.produce.common.base.constant.SystemStaticConst;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 错误捕获异常处理类，当出现报错的时候将错误数据用json的形式返回给页面
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public Map<String, Object> sqlExceptionHelper(HttpServletRequest req, SQLException e) {
        Map<String, Object> result = new HashMap<>();
        result.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
        result.put(SystemStaticConst.MSG, e.getMessage());
        return result;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> handleGlobalException(HttpServletRequest req, Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
        result.put(SystemStaticConst.MSG, e.getMessage());
        return result;
    }

    @ExceptionHandler(value = ClientAbortException.class)
    public void handleGlobalClientAbortException(HttpServletRequest req, ClientAbortException e) {
        logger.info("------------socket断开连接-------------" + e.getLocalizedMessage());
    }
}
