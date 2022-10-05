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

@Controller
public class LoginController implements CloudNoteConstant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProduce;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public JSONObject register(User user) {
        Map<String, Object> map = userService.register(user);
        JSONObject jsonObject = new JSONObject();
        //注册中没有问题 就跳到首页
        if (map == null || map.isEmpty()) {
            jsonObject.put("resultCode", 1);
            jsonObject.put("msg", "注册成功, 我们已经向您的邮箱发送了激活邮件, 请尽快激活");
            jsonObject.put("target", "/index");
            return jsonObject;
        } else {
            jsonObject.put("resultCode", 0);
            jsonObject.put("usernameMsg", map.get("usernameMsg"));
            jsonObject.put("passwordMsg", map.get("passwordMsg"));
            jsonObject.put("emailMsg", map.get("emailMsg"));
            jsonObject.put("user", user);
            return jsonObject;
        }
    }

    // http://localhost:8080/community/activation/101(用户 id)/code(激活码)
    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public JSONObject activation(Model model,
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
        return jsonObject;
    }

    /**
     * 获取验证码图片 需要用请求 HttpServletResponse 对象 以及 Session 返回图片
     * @param response
     */
    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpServletRequest request/*, HttpSession session*/) {

        //生成验证码以及验证码图片
        String text = kaptchaProduce.createText();

        BufferedImage image = kaptchaProduce.createImage(text);

        //将验证码存入 Session
        HttpSession session = request.getSession();
        session.setAttribute("kaptcha", text);

        //验证码的归属
        String kaptchaOwner = NoteUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner", kaptchaOwner);
        cookie.setMaxAge(60);
        cookie.setPath(contextPath);
        response.addCookie(cookie);

        //将图片返回给浏览器
        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            /**
             * image 输出的是哪一个图片
             * "png" 以什么格式输出
             * outputStream 用哪一个输出流输出
             */
            ImageIO.write(image, "png", outputStream);

        } catch (IOException e) {
            logger.error("响应验证码失败" + e.getMessage());
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public JSONObject login(String username, String password, String code, boolean rememberMe,
                        Model model,/* HttpSession session*/ HttpServletResponse response,
                        HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        //判断验证码
        HttpSession session = request.getSession();
        String kaptcha = (String) session.getAttribute("kaptcha");

        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            jsonObject.put("codeMsg", "验证码错误");
            return jsonObject;
        }
        //检查账号 密码
        int expiredSeconds = (rememberMe ? REMEBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS);
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")) {
            Cookie ticket = new Cookie("ticket", map.get("ticket").toString());
            ticket.setPath(contextPath);
            ticket.setMaxAge(expiredSeconds);
            response.addCookie(ticket);
            jsonObject.put("ticket",  map.get("ticket").toString());
            return jsonObject;
        } else {
            jsonObject.put("usernameMsg", map.get("usernameMsg"));
            jsonObject.put("passwordMsg", map.get("passwordMsg"));
            return jsonObject;
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public void logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
    }

}
