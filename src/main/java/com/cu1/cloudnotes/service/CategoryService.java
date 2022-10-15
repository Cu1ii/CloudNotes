package com.cu1.cloudnotes.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    List<JSONObject> getAllCategory(int userId);

    int removeCategory(int categoryId);

    int updateCategoryName(int categoryId, String newName);

    int changeCategoryCoverUrl(int categoryId, String newUrl);

}
