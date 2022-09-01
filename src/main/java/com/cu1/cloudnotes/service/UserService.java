package com.cu1.cloudnotes.service;


import com.cu1.cloudnotes.dao.LoginTicketMapper;
import com.cu1.cloudnotes.dao.UserMapper;
import com.cu1.cloudnotes.entity.LoginTicket;
import com.cu1.cloudnotes.entity.User;
import com.cu1.cloudnotes.utils.CloudNoteConstant;
import com.cu1.cloudnotes.utils.MailClient;
import com.cu1.cloudnotes.utils.NoteUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Service
public class UserService implements CloudNoteConstant {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;


    @Autowired
    private LoginTicketMapper loginTicketMapper;

    //域名
    @Value("${cloudnote.path.domain}")
    private String domain;

    //项目路径
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public Map<String, Object> register(User user) {
        HashMap<String, Object> map = new HashMap<>();
        //对空值进行判断处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        //可以在前端设置不允许表单提交空值
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }

        //验证账号是不是存在
        User u = userMapper.selectByName(user.getUsername());
        if (u != null) {
            map.put("usernameMsg", "该账号已存在");
            return map;
        }
        //判断邮箱是不是存在
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            map.put("emailMsg", "该邮箱已被注册");
            return map;
        }
        //注册用户 给用户一个随机序号 并将原密码覆盖为 MD5 加密后的密码
        user.setSalt(NoteUtil.generateUUID().substring(0, 5));
        user.setPassword(NoteUtil.MD5(user.getPassword()));

        //初始用户的状态为未激活
        user.setStatus(0);
        //获取激活码
        user.setActivationCode(NoteUtil.generateUUID());
        //给用户设置随机头像
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        //设置注册时间
        user.setCreateTime(new Date());

        userMapper.insertUser(user);

        //给用户发激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // 设置以什么路径发送邮件
        // http://localhost:8080/community/activation/101(用户 id)/code(激活码)
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content); //发送邮件

        return map;
    }

    /**
     * @param userId 用户 Id
     * @param code 激活码
     * @return 返回激活状态
     */
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        //如果已经激活过
        if (user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1); //激活码对应之后把激活状态改为激活
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }


    /**
     * 验证用户登录的凭证
     * @param username 用户名
     * @param password 密码
     * @param expiredSeconds 用户登录凭证在多长时间内过期
     * @return 返回登录验证的情况
     */
    public Map<String, Object> login(String username, String password, int expiredSeconds) {

        HashMap<String, Object> map = new HashMap<>();

        //空值处理
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空");
            return map;
        }

        //验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在");
            return map;
        }

        //验证密码
        password = NoteUtil.MD5(password); //user.getSalt());

        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确");
            return map;
        }

        //判断是否激活
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活");
            return map;
        }

        //生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(NoteUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);


        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    public void logout(String ticket) {
        loginTicketMapper.updateStatus(ticket, 1);
    }

    public LoginTicket findLoginTikcet(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);
    }

    /**
     * 更新用户头像
     * @param userId
     * @param headerUrl
     * @return
     */
    public int updateHeader(int userId, String headerUrl) {
        int rows = userMapper.updateHeader(userId, headerUrl);
        return rows;
    }

    /**
     * 根据用户名查询用户
     * @return 查询得到的用户
     * @param toName
     */
    public User findUserByName(String toName) {
        return userMapper.selectByName(toName);
    }


    public User findUserById(int id) {
        User user = userMapper.selectById(id);
        return user;
    }
}