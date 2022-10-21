package com.cu1.cloudnotes.dao;

import com.cu1.cloudnotes.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> selectByUserId(int userId);

    List<String> selectCategoryCoverUrlByUserId(int userId);

    int deleteByCategoryId(int id);

    int updateCategoryName(int id, String categoryName);

    int updateCategoryCoverUrl(int id, String categoryCoverUrl);

    int insertCategory(Category category);
}
