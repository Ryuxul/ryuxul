package com.example.recommendation.common.Alink;

import com.alibaba.alink.pipeline.LocalPredictor;
import com.alibaba.alink.pipeline.PipelineModel;
import org.apache.flink.types.Row;
import org.springframework.stereotype.Component;

@Component
public class AlinkPredict {
    public  Row predict(String msg) throws Exception {
        PipelineModel model = PipelineModel.load("src/main/java/com/example/recommendation/common/Alink/model");
        LocalPredictor localPredictor = model.collectLocalPredictor("title string");
        Row row = Row.of(msg);
        Row map = localPredictor.map(row);
        return map;
    }
}
