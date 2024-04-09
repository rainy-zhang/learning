package org.rainy.learning.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class ManualCommitSyncWithAsyncConsumer extends AbstractConsumer {

    // 混合同步提交与异步提交,
    // 在正常情况下偶然的提交失败并不是什么大问题,因为后续的提交成功会把提交失败的位移覆盖掉,但是在某些情况下,
    // 比如程序退出了,我们希望最后的提交也能够成功就可以使用混合同步提交与异步提交
    public static void main(String[] args) {
        // 设置手动提交位移
        properties.put("auto.commit.offset", false);
        consumer = new KafkaConsumer<>(properties);
        // 订阅topic
        consumer.subscribe(Collections.singleton("kafka-learning"));

        while (true) {
            boolean flag = true;
            // 调用poll方法拉取数据
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                log.info("topic = {}, partition = {}, key = {}, value = {}", record.topic(), record.partition(), record.key(), record.value());
                if (record.value().equals("done")) {
                    flag = false;
                }
            }

            try {
                // 混合同步提交与异步提交
                //先进行异步提交
                consumer.commitAsync();
            } catch (Exception e) {
                log.error("async commit failed", e);
            } finally {
                // 如果异步提交失败了, 则使用同步提交来尽最大可能保证提交成功, 因为同步提交本身就有重试的过程
                try {
                    consumer.commitSync();
                } finally {
                    consumer.close();
                }
            }

            if (!flag)
                break;
        }

    }

}
