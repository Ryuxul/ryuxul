package com.example.recommendation.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class WeChatProperties {
    private String appid="wxce3aea0b532c3d4f"; //小程序的appid
    private String secret="395d77ef7d85de81dafeeac195c95f39"; //小程序的秘钥
}
