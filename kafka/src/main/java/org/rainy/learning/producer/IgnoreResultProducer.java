package org.rainy.learning.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class IgnoreResultProducer extends AbstractProducer {

    // 向Kafka推送消息，不关心结果
    public static void main(String[] args) {
        ProducerRecord<String, String> record = new ProducerRecord<>("kafka-learning", "name", "ForgetResult");
        producer.send(record);
        producer.close();
        log.info("send success!");
    }

}
