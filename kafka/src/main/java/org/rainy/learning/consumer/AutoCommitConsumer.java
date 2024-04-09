package org.rainy.learning.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

/**
 * <p>
 * 自动提交位移
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class AutoCommitConsumer extends AbstractConsumer {

    // 自动提交消息位移
    public static void main(String[] args) {
        // 设置自动提交位移
        properties.put("enable.auto.commit", true);
        consumer = new KafkaConsumer<>(properties);
        // 订阅topic
        consumer.subscribe(Collections.singleton("kafka-learning"));
        // 循环拉取kafka中的消息
        try {
            while (true) {
                boolean flag = true;
                // 调用poll拉取数据
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
                for (ConsumerRecord<String, String> record : records) {
                    log.info("receive message: topic = {}, partition = {}, key = {}, value = {}", record.topic(), record.partition(), record.key(), record.value());
                    if (record.value().equals("done")) {
                        flag = false;
                    }
                }
                if (!flag) {
                    break;
                }
            }
        } finally {
            consumer.close();
        }
    }

}
