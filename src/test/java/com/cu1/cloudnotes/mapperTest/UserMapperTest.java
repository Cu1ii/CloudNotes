package com.cu1.cloudnotes.mapperTest;

import com.cu1.cloudnotes.CloudNotesApplication;
import com.cu1.cloudnotes.dao.CategoryMapper;
import com.cu1.cloudnotes.dao.LoginTicketMapper;
import com.cu1.cloudnotes.dao.NoteMapper;
import com.cu1.cloudnotes.dao.UserMapper;
import com.cu1.cloudnotes.entity.Note;
import com.cu1.cloudnotes.entity.User;
import com.cu1.cloudnotes.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CloudNotesApplication.class)
public class UserMapperTest {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("bigw@163.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        userService.register(user);

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

}
