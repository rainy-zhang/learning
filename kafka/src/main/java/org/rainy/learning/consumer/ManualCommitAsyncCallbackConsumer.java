package org.rainy.learning.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

/**
 * <p>
 * 手动异步提交消息位移带回调函数
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class ManualCommitAsyncCallbackConsumer extends AbstractConsumer {

    // 手动异步提交消息位移带回调函数 会记录下失败的情况
    public static void main(String[] args) {
        // 设置手动提交位移
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        // 订阅topic
        consumer.subscribe(Collections.singleton("kafka-learning"));

        while(true) {
            boolean flag = true;
            // 调用poll方法拉取数据
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));
            for (ConsumerRecord<String, String> record : records) {
                log.info("topic = {}, partition = {}, key = {}, value = {}", record.topic(), record.partition(), record.key(), record.value());
                if (record.value().equals("done")) {
                    flag = false;
                }
            }

            // 回调方法
            consumer.commitAsync((map, e) -> {
                // 如果想进行重试同时又保证消息顺序的话, 可以使用单调递增的序号,
                // 就是说每次发起异步提交的时候增加一个序号,并且将这个序号作为参数传递给回调方法,当消息提交失败回调时,检查参数中的序号值与全局的序号值是否相等,如果相等就说明没有新的消息,就可以进行重试,否则就放弃提交,因为已经有更新的位移提交了
                // 对于大部分情况下,位移提交失败是非常少见的
                if (e != null) {
                    log.info("commit failed for offset: {}", e.getMessage());
                }
            });
            if (!flag)
                break;
        }
    }

}
