package com.lusiwei.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 21:57
 * @Description:
 */
@Setter
@Getter
@ToString
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String phone;
    private int role;
    private Date createTime;
    private Date updateTime;
}
