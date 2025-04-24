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
public class UserHistory {
    //用户id
    private Long id;
    //农业信息id
    private Integer agricultureInfomationId;
    //农业信息标题
    private String agricultureInfomationTitle;
    //农业类型id
    private Integer categoryId;
    //农业信息详情
    private String detail;
    //创建时间
    private LocalDateTime browseTime;
}
