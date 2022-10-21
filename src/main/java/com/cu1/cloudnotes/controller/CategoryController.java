package com.cu1.cloudnotes.controller;

import com.alibaba.fastjson.JSONObject;
import com.cu1.cloudnotes.service.CategoryService;
import com.cu1.cloudnotes.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/showall")
    public String showAllCategory(int userId) {
        return null;
    }

}
