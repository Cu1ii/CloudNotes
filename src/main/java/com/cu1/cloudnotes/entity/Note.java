package com.cu1.cloudnotes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    private int id;
    private int userId;
    private String title;
    private String content;
    private int status;
    private Date createTime;

}
