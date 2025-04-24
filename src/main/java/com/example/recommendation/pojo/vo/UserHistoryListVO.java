package com.example.recommendation.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHistoryListVO implements Serializable {

    private Integer agricultureInfomationId;
    private String agricultureInfomationTitle;
    private String detail;
    private Integer categoryId;
    private String categoryName;
    private String imgUrl;
}
