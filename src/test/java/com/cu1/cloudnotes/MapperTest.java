package com.cu1.cloudnotes;

import com.cu1.cloudnotes.dao.CategoryMapper;
import com.cu1.cloudnotes.dao.LoginTicketMapper;
import com.cu1.cloudnotes.dao.NoteMapper;
import com.cu1.cloudnotes.dao.UserMapper;
import com.cu1.cloudnotes.entity.Note;
import com.cu1.cloudnotes.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CloudNotesApplication.class)
public class MapperTest {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void testSelectUser() {
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("liubei");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(user);
    }

    @Test
    public void testUpdateUser() {
        int rows = userMapper.updateHeader(150, "http://www.nowcoder.com/102.png");
        System.out.println(rows);

        rows = userMapper.updatePassword(150, "hello");
        System.out.println(rows);

        rows = userMapper.updateStatus(150, 1);
        System.out.println(rows);
    }

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

        Note note = noteMapper.selectById(1);
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
