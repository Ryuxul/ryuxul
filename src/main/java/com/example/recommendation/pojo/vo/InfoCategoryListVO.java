package com.example.recommendation.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoCategoryListVO {
    private Integer agricultureInfomationId;
    private String agricultureInfomationTitle;
    private Integer categoryId;
    private String categoryName;
    private String detail;
    private String imgUrl;
    private Long count;
    private Long hot;
}
