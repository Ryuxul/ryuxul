package com.example.recommendation.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgricultureInfomation implements Serializable {
    private Integer agricultureInfomationId;
    private String agricultureInfomationTitle;
    private Integer categoryId;
    private String detail;
    private LocalDateTime createTime;
}
