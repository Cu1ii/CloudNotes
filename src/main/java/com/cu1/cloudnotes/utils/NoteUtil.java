package com.cu1.cloudnotes.utils;


import com.alibaba.fastjson.JSONObject;
import com.cu1.cloudnotes.entity.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class NoteUtil {

    // 随机生成字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * MD5 单向加密
     * @param key 传入的明文密码
     * @return 加密后的 MD5
     */
    public static String MD5(String key) {
        if (StringUtils.isBlank(key)) return null;
        return DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
    }

    public static String getJsonString(int code, String msg, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code", code);
        jsonObject.put("msg", msg);

        if (map != null) {
            for (String key: map.keySet()) {
                jsonObject.put(key, map.get(key));
            }
        }
        return jsonObject.toJSONString();
    }

    public static String getJsonString(int code, String msg) {
        return getJsonString(code, msg, null);
    }

    public static String getJsonString(int code) {
        return getJsonString(code, null, null);
    }


    public static JSONObject getCategoryToJsonObject(final Category category) {
        JSONObject result = new JSONObject();
        result.put("id", category.getId());
        result.put("userId", category.getUserId());
        result.put("categoryName", category.getCategoryName());
        result.put("categoryCoverUrl", category.getCategoryCoverUrl());
        return result;
    }

}
