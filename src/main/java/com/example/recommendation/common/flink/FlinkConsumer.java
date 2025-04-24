package com.example.recommendation.common.flink;

import com.example.recommendation.pojo.entity.FlinkResult;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Properties;
@Component
public class FlinkConsumer {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env =  StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop101:9092,hadoop102:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test");
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>("first", new SimpleStringSchema(), properties);

        DataStreamSource<String> dataStreamSource = env.addSource(kafkaConsumer);
        SingleOutputStreamOperator<Tuple3<Long, Integer, Integer>> process = dataStreamSource.flatMap(
                new FlatMapFunction<String, Tuple3<Long, Integer, Integer>>() {
                    @Override
                    public void flatMap(String s, Collector<Tuple3<Long, Integer, Integer>> collector) throws Exception {
                        String[] split = s.split(",");
                        collector.collect(Tuple3.of(Long.valueOf(split[0]), Integer.valueOf(split[1]), 1));
                    }
                }
        ).keyBy(s -> s.f1).countWindow(10, 3).reduce(
                (value1, value2) -> Tuple3.of(value1.f0, value1.f1, value1.f2 + value2.f2)
        );
        SingleOutputStreamOperator<FlinkResult> streamOperator = process.map(s -> {
            return FlinkResult.builder().id(s.f0).categoryId(s.f1).weight(s.f2).build();
        });
        streamOperator.addSink(new MySqlSink());

        process.print();
        env.execute("flink start");
    }

    public static class MySqlSink extends RichSinkFunction<FlinkResult> {
        private Connection connection;
        private PreparedStatement insert;

        @Override
        public void open(Configuration parameters) throws Exception {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/recommendation?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true", "root", "278956");
            insert = connection.prepareStatement("insert into flink_result(id,category_id,weight,create_time) values(?,?,?,?)");
            System.out.println("FlinkToMysql open");
        }

        @Override
        public void invoke(FlinkResult value, Context context) throws Exception {
            insert.setLong(1, value.getId());
            insert.setInt(2, value.getCategoryId());
            insert.setInt(3, value.getWeight());
            insert.setString(4, LocalDateTime.now().toString());
            insert.execute();
        }

        @Override
        public void close() throws Exception {
            connection.close();
        }
    }
}
