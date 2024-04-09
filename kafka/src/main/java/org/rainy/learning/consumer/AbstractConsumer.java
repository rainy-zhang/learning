package org.rainy.learning.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class AbstractConsumer {

    public static KafkaConsumer<String, String> consumer;
    public static final Properties properties;

    static {
        properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 指定消费者组, 每个消费者组都可以消费kafka中的全量信息
        properties.put("group.id", "kafka-learning");
    }

}
