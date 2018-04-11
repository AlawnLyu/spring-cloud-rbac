package com.base.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonHelper {

  private static Gson gson = null;
  private static ObjectMapper objectMapper = null;

  static {
    gson = new Gson();
    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
  }

  public static Object toObject(String json, Class clz) {
    JsonElement elem = new JsonParser().parse(json).getAsJsonObject();
    return gson.fromJson(elem, clz);
  }

  public static synchronized Gson newGsonInstance() {
    if (gson == null) {
      gson = new Gson();
    }
    return gson;
  }

  public static synchronized ObjectMapper newObjectMapperInstance() {
    if (objectMapper == null) {
      objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
      objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    return objectMapper;
  }

  /**
   * 获取泛型的Collection Type
   *
   * @param collectionClass 泛型的Collection
   * @param elementClasses 元素类
   * @return JavaType Java类型
   * @since 1.0
   */
  public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
    return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
  }

  public static <T> T readObject(String value, Class<T> tClass) throws IOException {
    return objectMapper.readValue(value, tClass);
  }

  public static <T> T readObject(String value, TypeReference valueTypeRef) throws IOException {
    return objectMapper.readValue(value, valueTypeRef);
  }

  public static Object readObject(String value, JavaType javaType) throws IOException {
    return objectMapper.readValue(value, javaType);
  }

  public static <T> String writeObject(T t) throws IOException {
    return objectMapper.writeValueAsString(t);
  }

  public static String toJson(Object obj) {
    return gson.toJson(obj);
  }

  public static <T> Map<String, T> toMap(String json, Class<T> clz) {
    Map<String, JsonObject> map =
        gson.fromJson(json, new TypeToken<Map<String, JsonObject>>() {}.getType());
    Map<String, T> result = new HashMap<>();
    for (String key : map.keySet()) {
      result.put(key, gson.fromJson(map.get(key), clz));
    }
    return result;
  }

  public static Map<String, Object> toMap(String json) {
    Map<String, Object> map =
        gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
    return map;
  }

  public static <T> List<T> toList(String json, Class<T> clz) {
    JsonArray array = new JsonParser().parse(json).getAsJsonArray();
    List<T> list = new ArrayList<>();
    for (final JsonElement elem : array) {
      list.add(gson.fromJson(elem, clz));
    }
    return list;
  }

  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  // 将JSON转换成Map,其中valueClz为Map中value的Class,keyArray为Map的key
  public static Map json2Map(Object[] keyArray, String json, Class valueClz) {
    JSONObject jsonObject = JSONObject.fromObject(json);
    Map classMap = new HashMap();
    for (int i = 0; i < keyArray.length; i++) {
      classMap.put(keyArray[i], valueClz);
    }
    return (Map) JSONObject.toBean(jsonObject, Map.class, classMap);
  }

  /**
   * * 将对象转换为传入类型的List
   *
   * @param object
   * @param objectClass
   * @return
   */
  public static <T> Collection<T> toList(Object object, Class<T> objectClass) {
    JSONArray jsonArray = JSONArray.fromObject(object);
    return JSONArray.toCollection(jsonArray, objectClass);
  }

  /**
   * 功能描述：实现将一个object对象转换为json的string字符串
   *
   * @param obj
   * @return
   */
  public static String object2json(Object obj) {
    StringBuilder json = new StringBuilder();
    if (obj == null) {
      json.append("\"\"");
    } else if (obj instanceof String || obj instanceof BigDecimal || obj instanceof BigInteger) {
      json.append("\"").append(string2json(obj.toString())).append("\"");
    } else if (obj instanceof Boolean) {
      json.append(Boolean.valueOf(obj.toString()));
    } else if (obj instanceof Integer
        || obj instanceof Float
        || obj instanceof Short
        || obj instanceof Double
        || obj instanceof Long
        || obj instanceof Byte) {
      json.append(obj.toString());
    } else if (obj instanceof Date || obj instanceof java.sql.Date) {
      json.append("\"").append(sdf.format(obj)).append("\"");
    } else if (obj instanceof Object[]) {
      json.append(array2json((Object[]) obj));
    } else if (obj instanceof List) {
      json.append(list2json((List<?>) obj));
    } else if (obj instanceof Map) {
      json.append(map2json((Map<?, ?>) obj));
    } else if (obj instanceof Set) {
      json.append(set2json((Set<?>) obj));
    } else {
      json.append(bean2json(obj));
    }
    return json.toString();
  }

  public static String list2json(List<?> list) {
    StringBuilder json = new StringBuilder();
    json.append("[");
    if (list != null && list.size() > 0) {
      for (Object obj : list) {
        json.append(object2json(obj));
        json.append(",");
      }
      json.setCharAt(json.length() - 1, ']');
    } else {
      json.append("]");
    }
    return json.toString();
  }

  public static String array2json(Object[] array) {
    StringBuilder json = new StringBuilder();
    json.append("[");
    if (array != null && array.length > 0) {
      for (Object obj : array) {
        json.append(object2json(obj));
        json.append(",");
      }
      json.setCharAt(json.length() - 1, ']');
    } else {
      json.append("]");
    }
    return json.toString();
  }

  public static String map2json(Map<?, ?> map) {
    StringBuilder json = new StringBuilder();
    json.append("{");
    if (map != null && map.size() > 0) {
      for (Object key : map.keySet()) {
        json.append(object2json(key));
        json.append(":");
        json.append(object2json(map.get(key)));
        json.append(",");
      }
      json.setCharAt(json.length() - 1, '}');
    } else {
      json.append("}");
    }
    return json.toString();
  }

  public static String set2json(Set<?> set) {
    StringBuilder json = new StringBuilder();
    json.append("[");
    if (set != null && set.size() > 0) {
      for (Object obj : set) {
        json.append(object2json(obj));
        json.append(",");
      }
      json.setCharAt(json.length() - 1, ']');
    } else {
      json.append("]");
    }
    return json.toString();
  }

  public static String string2json(String s) {
    if (s == null) return "";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      switch (ch) {
        case '"':
          sb.append("\\\"");
          break;
        case '\\':
          sb.append("\\\\");
          break;
        case '\b':
          sb.append("\\b");
          break;
        case '\f':
          sb.append("\\f");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\t':
          sb.append("\\t");
          break;
        case '/':
          sb.append("\\/");
          break;
        default:
          if (ch >= '\u0000' && ch <= '\u001F') {
            String ss = Integer.toHexString(ch);
            sb.append("\\u");
            for (int k = 0; k < 4 - ss.length(); k++) {
              sb.append('0');
            }
            sb.append(ss.toUpperCase());
          } else {
            sb.append(ch);
          }
      }
    }
    return sb.toString();
  }

  public static String bean2json(Object bean) {
    StringBuilder json = new StringBuilder();
    json.append("{");
    PropertyDescriptor[] props = null;
    try {
      props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
    } catch (IntrospectionException e) {
    }
    if (props != null) {
      for (int i = 0; i < props.length; i++) {
        try {
          String name = object2json(props[i].getName());
          String value = object2json(props[i].getReadMethod().invoke(bean));
          json.append(name);
          json.append(":");
          json.append(value);
          json.append(",");
        } catch (Throwable e) {
        }
      }
      json.setCharAt(json.length() - 1, '}');
    } else {
      json.append("}");
    }
    return json.toString();
  }
}
