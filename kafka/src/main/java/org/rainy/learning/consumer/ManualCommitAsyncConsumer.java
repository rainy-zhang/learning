package org.rainy.learning.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

/**
 * <p>
 * 手动异步提交位移
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class ManualCommitAsyncConsumer extends AbstractConsumer {

    // 手动异步提交位移
    public static void main(String[] args) {
        // 设置手动提交位移
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        // 订阅topic
        consumer.subscribe(Collections.singleton("kafka-learning"));

        while (true) {
            boolean flag = true;
            // 调用poll方法拉取数据
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));
            for (ConsumerRecord<String, String> record : records) {
                log.info("topic = {}, partition = {}, key = {}, value = {}", record.topic(), record.partition(), record.key(), record.value());
                if (record.value().equals("done")) {
                    flag = false;
                }
            }
            // 使用consumer异步提交位移, 如果服务器返回提交失败,异步提交不会进行重试,同步提交会进行重试,直到提交成功或抛出CommitFailedException异常
            // commit A, offset 2000
            // commit B, offset 3000
            // A提交失败, B提交成功, A重新提交后成功, 那么会导致最后一次提交的offset为2000, 所有消费者都将从offset: 2000这个位置重新开始消费消息, 会导致2000-3000这段数据被重复消费
            consumer.commitAsync();

            if (!flag)
                break;
        }
    }

}
