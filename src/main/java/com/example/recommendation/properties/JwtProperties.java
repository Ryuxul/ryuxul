package com.example.recommendation.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtProperties {
    private String userSecretKey = "alancheng";
    private long userTtl = 7200000;
    private String userTokenName = "authentication";
}
