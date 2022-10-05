package com.cu1.cloudnotes.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;

    // 用户激活状态
    private int    status;

    private String activationCode;
    // 头像返回路径 做补充
    private String headerUrl;
    private Date   createTime;

}
