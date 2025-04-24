package com.example.recommendation.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserScore {

    private Long id;
    private Integer agricultureInfomationId;
    private Integer categoryId;
    private Integer score;
    private String agricultureInfomationTitle;
    private String detail;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
