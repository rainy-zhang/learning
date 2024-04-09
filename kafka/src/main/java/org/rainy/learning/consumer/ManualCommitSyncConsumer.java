package org.rainy.learning.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

/**
 * <p>
 * 手动同步提交位点
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class ManualCommitSyncConsumer extends AbstractConsumer {

    //手动同步提交位点, 提交poll返回的最后位点
    public static void main(String[] args) {
        // 设置手动提交位移
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        // 订阅topic
        consumer.subscribe(Collections.singleton("kafka-learning"));

        while(true) {
            boolean flag = true;
            // 调用poll方法拉取数据
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
            for (ConsumerRecord<String, String> record : records) {
                log.info("topic = {}, partition = {}, key = {}, value = {}", record.topic(), record.partition(), record.key(), record.value());
                if (record.value().equals("done")) {
                    flag = false;
                }
            }
            // 使用consumer同步提交位移, 调用commitSync()会导致当前线程阻塞,
            // 如果服务器返回提交失败,同步提交会进行重试,直到提交成功或抛出CommitFailedException异常
            try {
                consumer.commitSync();
            } catch (CommitFailedException e) {
                e.printStackTrace();
            }

            if (!flag)
                break;
        }
    }

}
