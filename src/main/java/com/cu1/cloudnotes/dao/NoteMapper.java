package com.cu1.cloudnotes.dao;

import com.cu1.cloudnotes.entity.Note;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoteMapper {
    List<Note> selectByCategoryId(int categoryId);
    Note selectById(int id);
    int insertNote(Note note);
    int deleteById(int id);
    int updateStatus(int id,int status);
    int updateTitle(int id,String title);
    int updateContent(int id,String content);
}
