package com.gateway.entity;

/** 错误信息实体 */
public class ErrorException {

  /** 报错的类 */
  private String exceptionClass;

  /** 错误的原因 */
  private String exceptionMessage;

  public String getExceptionClass() {
    return exceptionClass;
  }

  public void setExceptionClass(String exceptionClass) {
    this.exceptionClass = exceptionClass;
  }

  public String getExceptionMessage() {
    return exceptionMessage;
  }

  public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }
}
