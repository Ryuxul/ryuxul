package com.example.recommendation.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoAddDTO implements Serializable {
    private String title;
    private Integer categoryId;
    private String detail;

}
