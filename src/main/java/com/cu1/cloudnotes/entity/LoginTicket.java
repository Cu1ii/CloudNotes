package com.cu1.cloudnotes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginTicket {

    private int id;
    private int userId;
    private String ticket;
    private int status;

    // 到期日期
    private Date expired;

}
