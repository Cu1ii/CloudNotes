package com.cu1.cloudnotes.service;

import com.alibaba.fastjson.JSONObject;
import com.cu1.cloudnotes.dao.CategoryMapper;
import com.cu1.cloudnotes.dao.UserMapper;
import com.cu1.cloudnotes.entity.Category;
import com.cu1.cloudnotes.utils.NoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<JSONObject> getAllCategory(int userId) {
        List<Category> categories = categoryMapper.selectByUserId(userId);
        List<JSONObject> resultlist = new ArrayList<>();
        for (Category it: categories) {
            resultlist.add(NoteUtil.getCategoryToJsonObject(it));
        }
        return resultlist;
    }

    @Override
    public int removeCategory(int categoryId) {
        return categoryMapper.deleteByCategoryId(categoryId);
    }

    @Override
    public int updateCategoryName(int categoryId, String newName) {
        return categoryMapper.updateCategoryName(categoryId, newName);
    }

    @Override
    public int changeCategoryCoverUrl(int categoryId, String newUrl) {
        return categoryMapper.updateCategoryCoverUrl(categoryId, newUrl);
    }
}
