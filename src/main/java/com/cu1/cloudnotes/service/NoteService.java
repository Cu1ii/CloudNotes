package com.cu1.cloudnotes.service;

import com.cu1.cloudnotes.dao.NoteMapper;
import com.cu1.cloudnotes.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;

    public List<Note> findAllNotes(int categoryId) {
        return noteMapper.selectByCategoryId(categoryId);
    }

    public Note findNoteById(int id) {
        return noteMapper.selectById(id);
    }

    public int addNote(Note note) {
        return noteMapper.insertNote(note);
    }

    public int deleteNoteById(int id) {
        return noteMapper.deleteById(id);
    }

    public int updateNoteStatus(int id, int status) {
        return noteMapper.updateStatus(id, status);
    }

    public int updateTitle(int id, String title) {
        return noteMapper.updateTitle(id, title);
    }

    public int updateContent(int id, String content) {
        return noteMapper.updateContent(id, content);
    }

}
