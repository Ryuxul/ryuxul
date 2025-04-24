package com.example.recommendation.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlinkResult {
    private Long id;
    private Integer categoryId;
    private Integer weight;
}
