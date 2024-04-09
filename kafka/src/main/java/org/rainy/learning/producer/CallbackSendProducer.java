package org.rainy.learning.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
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
public class CallbackSendProducer extends AbstractProducer {

    // 向Kafka异步发送数据，在会调中处理发送成功或发送失败的逻辑
    public static void main(String[] args) {
        ProducerRecord<String, String> record = new ProducerRecord<>("kafka-learning", "name", "callback message1");
        producer.send(record, new MyProducerCallback());
        record = new ProducerRecord<>("kafka-learning", "name", "callback message2");
        producer.send(record, new MyProducerCallback());
        record = new ProducerRecord<>("kafka-learning", "name", "callback message3");
        producer.send(record, new MyProducerCallback());
        record = new ProducerRecord<>("kafka-learning", "name", "done");
        producer.send(record, new MyProducerCallback());
        producer.close();
    }

    // 自定义回调类, 需要实现org.apache.kafka.clients.producer.Callback接口
    private static class MyProducerCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (null != e) {
                e.printStackTrace();
                return;
            }
            log.info("Coming in MyProducerCallback topic: {}, partition: {}, offset: {}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
        }
    }

}
