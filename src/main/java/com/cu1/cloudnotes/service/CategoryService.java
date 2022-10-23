package com.cu1.cloudnotes.service;

import com.alibaba.fastjson.JSONObject;
import com.cu1.cloudnotes.dao.CategoryMapper;
import com.cu1.cloudnotes.entity.Category;
import com.cu1.cloudnotes.utils.NoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategory(int userId) {
        List<Category> categories = categoryMapper.selectByUserId(userId);
        return categories;
    }


    public int removeCategory(int categoryId) {
        return categoryMapper.deleteByCategoryId(categoryId);
    }


    public int updateCategoryName(int categoryId, String newName) {
        return categoryMapper.updateCategoryName(categoryId, newName);
    }


    public int changeCategoryCoverUrl(int categoryId, String newUrl) {
        return categoryMapper.updateCategoryCoverUrl(categoryId, newUrl);
    }

    public int insertCategory(Category category){
        return categoryMapper.insertCategory(category);
    }

}
