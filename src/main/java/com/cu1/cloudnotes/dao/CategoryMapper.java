package com.cu1.cloudnotes.dao;

import com.cu1.cloudnotes.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> selectByUserId(int userId);

    int deleteByCategoryName(int id);

    int updateCategoryName(int id,String categoryName);

    int insertCategory(Category category);
}
