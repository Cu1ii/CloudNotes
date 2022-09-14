package com.cu1.cloudnotes.controller;

import com.alibaba.fastjson.JSONObject;
import com.cu1.cloudnotes.annotation.LoginRequired;
import com.cu1.cloudnotes.entity.User;
import com.cu1.cloudnotes.service.UserService;
import com.cu1.cloudnotes.utils.CloudNoteConstant;
import com.cu1.cloudnotes.utils.HostHolder;
import com.cu1.cloudnotes.utils.NoteUtil;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController implements CloudNoteConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${cloudnote.path.upload}")
    public String uploadPath;

    @Value("${cloudnote.path.domain}")
    public String domain;

    @Value("${server.servlet.context-path}")
    public String contextPath;

    @Autowired
    private UserService userService;

    /**
     * 当前用户
     */
    @Autowired
    private HostHolder hostHolder;

    /**
     * 上传的时候表单上传方式必须要为 Post
     * @multipartFile 前端传过来的文件
     */
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public JSONObject uploadHeader(MultipartFile headerImage) {
        JSONObject jsonObject = new JSONObject();
        //文件不存在
        if (headerImage == null) {
            jsonObject.put("resultCode", "error");
            jsonObject.put("msg", "您还没有选择图片");
            return jsonObject;
        }

        //获取传的文件的原始名字
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            jsonObject.put("resultCode", "error");
            jsonObject.put("msg", "文件格式不正确");
            return jsonObject;
        }
        //生成随机文件名
        filename = NoteUtil.generateUUID() + suffix;

        //确定文件存放的路径
        File dest = new File(uploadPath + "/" + filename);

        try {
            //存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败:" + e.getMessage());
            throw new RuntimeException("上传文件失败, 服务器发生异常", e);
        }

        //跟新当前用户的头像的路径 http://localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        //允许 Web 访问的路径
        String headerUrl = domain + contextPath + "/user" + "/header/" + filename;
        userService.updateHeader(user.getId(), headerUrl);
        jsonObject.put("resultCode", "success");
        jsonObject.put("headerUrl", headerUrl);
        return jsonObject;
    }

    @RequestMapping(path = "/header/{filename}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response) {

        //找到服务器上存储的路径
        filename = uploadPath + "/" + filename;

        //文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."));

        //响应图片
        response.setContentType("image/" + suffix);
        try(
                FileInputStream fileInputStream = new FileInputStream(filename);
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, b);
            }
        } catch (IOException e) {

            logger.error("读取头像失败" + e.getMessage());
        }

    }

}