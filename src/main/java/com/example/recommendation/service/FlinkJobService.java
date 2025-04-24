package com.example.recommendation.service;

import com.example.recommendation.common.flink.FlinkConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlinkJobService {
    @Autowired
    private FlinkConsumer flinkConsumer;

    public void startJob() throws Exception {
        FlinkConsumer.main(new String[]{});
    }
}
