package com.gateway.util;

import com.gateway.entity.ErrorException;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/** 封装返回的错误信息工具类 */
public class CombineException {
  /**
   * 获取错误的消息
   *
   * @param throwable
   * @return
   */
  public static JSONObject getErrorException(Throwable throwable) {
    List<ErrorException> exceptionList = initException(throwable);
    JSONObject jobj = new JSONObject();
    for (ErrorException errorException : exceptionList) {
      if (errorException.getExceptionClass().indexOf("com.netflix.client.ClientException") != -1) {
        jobj.put("errorCode", "500");
        jobj.put("info", errorException.getExceptionMessage());
        jobj.put("msg", "服务器维护中！");
        return jobj;
      } else if (errorException.getExceptionClass().indexOf("java.util.concurrent.TimeoutException")
          != -1) {
        jobj.put("errorCode", "409");
        jobj.put("info", errorException.getExceptionMessage());
        jobj.put("msg", "服务器连接超时！");
        return jobj;
      }
    }
    if (exceptionList.size() > 0) {

      jobj.put("info", exceptionList.get(0).getExceptionMessage());
    }
    jobj.put("errorCode", "400");
    jobj.put("msg", "服务器响应发生错误！");
    return jobj;
  }

  /**
   * 实现获取错误信息
   *
   * @param throwable
   * @return
   */
  public static List<ErrorException> initException(Throwable throwable) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    throwable.printStackTrace(new PrintStream(baos));
    String exception = baos.toString();
    return recursionException(exception);
  }

  /**
   * 递归调用获取错误信息的集合
   *
   * @param exception
   * @return
   */
  private static List<ErrorException> recursionException(String exception) {
    List<ErrorException> exceptionList = new ArrayList<>();
    int start = exception.indexOf("Caused by:");
    if (start != -1) {
      int end = exception.substring(start).indexOf("\r\n\t");
      String exceptionInfo = exception.substring(start, start + end);
      String[] arr = exceptionInfo.split(":");
      if (arr != null && arr.length >= 3) {
        ErrorException errorException = new ErrorException();
        errorException.setExceptionClass(arr[1]);
        errorException.setExceptionMessage(arr[2]);
        exceptionList.add(errorException);
      } else {
        ErrorException errorException = new ErrorException();
        errorException.setExceptionClass(arr[1]);
        errorException.setExceptionMessage("");
        exceptionList.add(errorException);
      }
      if (!exception.substring(start + end).equals("")) {
        exceptionList.addAll(recursionException(exception.substring(start + end)));
      }
    }
    return exceptionList;
  }
}
