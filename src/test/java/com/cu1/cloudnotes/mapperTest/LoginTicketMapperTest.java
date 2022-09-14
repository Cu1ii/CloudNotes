package com.cu1.cloudnotes.mapperTest;

import com.cu1.cloudnotes.CloudNotesApplication;
import com.cu1.cloudnotes.dao.LoginTicketMapper;
import com.cu1.cloudnotes.entity.LoginTicket;
import com.cu1.cloudnotes.entity.User;
import com.cu1.cloudnotes.utils.NoteUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;


@SpringBootTest
@ContextConfiguration(classes = CloudNotesApplication.class)
public class LoginTicketMapperTest {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    User user;

    LoginTicket loginTicket;

    String ticket;

    {
        user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());
        loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        ticket = NoteUtil.generateUUID();
        loginTicket.setTicket(ticket);
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 10 * 1000));
    }

    @Test
    void insertLoginTicketTest() {

        int i = loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println(i);
    }

    @Test
    void selectLoginTicketTest() {
        LoginTicket ticket1 = loginTicketMapper.selectByTicket("6ffe6259b2df43e09643c46cacc735fe");
        System.out.println(ticket1);
    }

    @Test
    void updateLoginTicketTest() {
        int i = loginTicketMapper.updateStatus("6ffe6259b2df43e09643c46cacc735fe", 0);
        System.out.println(i);
    }


}
