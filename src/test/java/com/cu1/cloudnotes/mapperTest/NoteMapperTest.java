package com.cu1.cloudnotes.mapperTest;

import com.cu1.cloudnotes.CloudNotesApplication;
import com.cu1.cloudnotes.dao.NoteMapper;
import com.cu1.cloudnotes.entity.Note;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CloudNotesApplication.class)
public class NoteMapperTest {

    @Autowired
    private NoteMapper noteMapper;

    @Test
    public void testInsertNote() {
        Note note = new Note();
        note.setCategoryId(1);
        note.setTitle("niu");
        note.setStatus(1);
        note.setContent("666");
        note.setCreateTime(new Date());

        int rows = noteMapper.insertNote(note);
        System.out.println(rows);
        System.out.println(note.getId());

    }

    @Test
    public void testSelectNote() {

        List<Note> notes = noteMapper.selectByCategoryId(1);
        for (Note note : notes) {
            System.out.println(note);
        }

        Note note =noteMapper.selectById(1);
        System.out.println(note);
    }

    @Test
    public void testDeleteNote() {
        int rows = noteMapper.deleteById(1);
        System.out.println(rows);
    }

    @Test
    public void testUpdateNote() {
        int rows = noteMapper.updateStatus(1, 0);
        System.out.println(rows);

        rows = noteMapper.updateTitle(1, "hello");
        System.out.println(rows);

        rows = noteMapper.updateContent(1, "111");
        System.out.println(rows);
    }


}
