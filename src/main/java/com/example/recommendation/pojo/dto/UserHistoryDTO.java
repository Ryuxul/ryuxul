package com.example.recommendation.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHistoryDTO {
    private Long id;
    private Integer agricultureInfomationId;
    private String agricultureInfomationTitle;
    private String detail;
    private Integer categoryId;
    private String categoryName;
    private String imgUrl;
}
