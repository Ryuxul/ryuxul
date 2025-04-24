package com.example.recommendation.pojo.dto;

import lombok.Data;

import java.io.Serializable;
// 微信用户登录id
@Data
public class UserLoginDTO implements Serializable {

    private String code;

}
