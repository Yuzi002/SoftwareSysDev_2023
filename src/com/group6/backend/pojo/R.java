package com.group6.backend.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * 将需要返回的数据保存在对象中，然后再将该对象统一返回数据
 */
public class R extends HashMap<String, Object> {

  private static final long serialVersionUID = 1L;

  public R() {
    put("code", 1);
  }

  public static R error() {
    return error(0, "未知异常，请联系管理员");
  }

  public static R error(String msg) {
    return error(0, msg);
  }

  public static R error(int code, String msg) {
    R r = new R();
    r.put("code", code);
    r.put("msg", msg);
    return r;
  }

  public static R ok(String msg) {
    R r = new R();
    r.put("msg", msg);
    return r;
  }

  public static R ok(Map<String, Object> map) {
    R r = new R();
    r.putAll(map);
    return r;
  }

  public static R ok() {
    return new R();
  }

  public R put(String key, Object value) {
    super.put(key, value);
    return this;
  }
}

