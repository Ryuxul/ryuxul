package com.example.recommendation;

import com.example.recommendation.service.FlinkJobService;
import com.example.recommendation.utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecommendationApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RecommendationApplication.class, args);
        FlinkJobService flinkJobService = SpringUtils.getBean(FlinkJobService.class);
        flinkJobService.startJob();
    }

}
