package com.cu1.cloudnotes.controller;

import com.alibaba.fastjson.JSONObject;
import com.cu1.cloudnotes.entity.Category;
import com.cu1.cloudnotes.service.CategoryService;
import com.cu1.cloudnotes.utils.NoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/showall/{userId}")
    public List<Category> showAllCategory(@PathVariable("userId") int userId) {
        return categoryService.getAllCategory(userId);
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam int userId,
                              @RequestParam String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setUserId(userId);
        category.setCreateTime(new Date());
        // 添加分类
        categoryService.insertCategory(category);
        return NoteUtil.getJsonString(0, "添加分类成功!");
    }

    @PostMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable int categoryId) {
        categoryService.removeCategory(categoryId);
        return NoteUtil.getJsonString(0, "删除分类成功!");
    }

    @PostMapping("/updateName/{categoryId}")
    public String updateCategoryName(@PathVariable int categoryId,
                                     @RequestParam String newName) {
        categoryService.updateCategoryName(categoryId, newName);
        return NoteUtil.getJsonString(0, "修改分类名称成功!");
    }

    @PostMapping("/updateCover/{categoryId}")
    public String updateCoverUrl(@PathVariable int categoryId,
                                 @RequestParam String newUrl) {
        categoryService.changeCategoryCoverUrl(categoryId, newUrl);
        return NoteUtil.getJsonString(0, "修改封面成功!");
    }


}
