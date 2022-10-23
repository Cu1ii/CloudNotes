package com.cu1.cloudnotes.controller;

import com.alibaba.fastjson.JSONObject;
import com.cu1.cloudnotes.entity.User;
import com.cu1.cloudnotes.service.UserService;
import com.cu1.cloudnotes.utils.CloudNoteConstant;
import com.cu1.cloudnotes.utils.NoteUtil;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class LoginController implements CloudNoteConstant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProduce;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Map<String, Object> map = userService.register(user);
        JSONObject jsonObject = new JSONObject();
        //注册中没有问题 就跳到首页
        if (map == null || map.isEmpty()) {
            jsonObject.put("resultCode", 1);
            jsonObject.put("msg", "注册成功, 我们已经向您的邮箱发送了激活邮件, 请尽快激活");
            jsonObject.put("target", "/index");
            return jsonObject.toJSONString();
        } else {
            jsonObject.put("resultCode", 0);
            jsonObject.put("usernameMsg", map.get("usernameMsg"));
            jsonObject.put("passwordMsg", map.get("passwordMsg"));
            jsonObject.put("emailMsg", map.get("emailMsg"));
            jsonObject.put("user", user);
            return jsonObject.toJSONString();
        }
    }

    // http://localhost:8080/community/activation/101(用户 id)/code(激活码)
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model,
                             @PathVariable("userId") int userId,
                             @PathVariable("code") String code) {
        int result = userService.activation(userId, code);
        JSONObject jsonObject = new JSONObject();
        //处理激活结果
        if (result == ACTIVATION_SUCCESS) {
            jsonObject.put("msg", "激活成功, 您的账号已经可以正常使用");
            jsonObject.put("target", "/login");
        } else if (result == ACTIVATION_REPEAT) {
            jsonObject.put("msg", "无效操作, 该账号已经激活");
            jsonObject.put("target", "/index");
        } else {
            jsonObject.put("msg", "激活失败, 您提供的激活码不正确");
            jsonObject.put("target", "/index");
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username, @RequestParam String password) {
        JSONObject jsonObject = new JSONObject();
        //检查账号 密码
        int expiredSeconds = DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        jsonObject.put("usernameMsg", map.get("usernameMsg"));
        jsonObject.put("passwordMsg", map.get("passwordMsg"));
        jsonObject.put("userId", map.get("userId"));
        jsonObject.put("username", map.get("username"));
        jsonObject.put("headerUrl", map.get("headerUrl"));

        System.out.println(jsonObject.toJSONString());

        return jsonObject.toJSONString();
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public void logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
    }

}
