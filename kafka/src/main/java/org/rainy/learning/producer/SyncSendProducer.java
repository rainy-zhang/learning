package org.rainy.learning.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class SyncSendProducer extends AbstractProducer {

    // 向Kafka同步发送数据
    public static void main(String[] args) throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<>("kafka-learning", "name", "sync message");
        // 调用get()方法, 会等待kafka集群的响应. 当发送失败时会抛出异常.
        // 1. 不可恢复的异常(如:发送的消息过大), 2. 可恢复的异常broker会进行重试,一定次数之后才会抛出异常(如:连接超时)
        RecordMetadata result = producer.send(record).get();
        log.info("send success, topic: {}, partition: {}, offset: {}", result.topic(), result.partition(), result.offset());
    }

}
