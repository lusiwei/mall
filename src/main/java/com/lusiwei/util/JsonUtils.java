package com.lusiwei.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @Author: lusiwei
 * @Date: 2018/12/5 09:47
 * @Description:
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
    }

    public static <T> String object2Json(T t) {
        if (t == null) {
            return null;
        }
        try {
            return t instanceof String ? String.valueOf(t) : OBJECT_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("object to json exception,Object:{},exception", t, e);
            return null;
        }
    }
    public static <T> T string2Object(String str, TypeReference<T> tTypeReference){
        if (str == null || tTypeReference == null) {
            return null;
        }
        try {
            return tTypeReference.getType().equals(String.class) ? (T)str : OBJECT_MAPPER.readValue(str, tTypeReference);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("str to object error,str:{},exception:{}",str,e);
            return null;
        }

    }

    public static <T> T string2Object(String str, Class<T> tClass) {
        if (str == null) {
            return null;
        }
        try {
            return  tClass.equals(String.class)?(T)str:OBJECT_MAPPER.readValue(str,tClass);
        } catch (IOException e) {
            log.error("JsonUtil: string2object exception,String:{},clazz:{},exception:{}",str,tClass,e);
            return null;
        }
    }
}
