package org.rainy.learning.producer;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class AbstractProducer {

    public static final KafkaProducer<String, String> producer;

    static {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //配置自定义分区分配器
        properties.put("partitioner.class", "org.rainy.learning.partition.CustomPartitioner");

        producer = new KafkaProducer<>(properties);
    }

}
