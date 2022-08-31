package com.cu1.cloudnotes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private int id;
    private int userId;
    private String categoryName;
    private Date createTime;
}
