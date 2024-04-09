package org.rainy.learning.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * 自定义分区分配器
 *
 * @author zhangyu
 */
public class CustomPartitioner implements Partitioner {

    /*
     * Message中key的作用:
     * kafka会根据message中的key来决定消息写入哪个分区(partition), 所有具有相同key的消息会被写入到同一个partition中
     * key为null时,kafka会使用默认的分区分配器,round-robin实现负载均衡
     * 存在key时,kafka会使用默认的分区分配器,对key进行hash确定消息应该分配到哪个分区
     * 默认的分区分配器: default partitioner会使用轮训的的算法,来将消息写入到分区中来实现负载均衡
     * 计算消息与分区的映射关系,计算的是全部的分区,而不是可用的分区,所以当消息被分配到不可用的分区时会写入失败,
     * 如果需要加入新的分区那么消息与分区的映射也会发生变化,应避免这种情况发生
     */

    /**
     * 自定义分区
     *
     * @param topic      topic
     * @param key        消息的key
     * @param keyBytes   key的字节表示
     * @param value      消息的value
     * @param valueBytes value的字节表示
     * @param cluster    kafka集群的信息
     * @return 分区号
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 获取topic中所有的partition
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int numPartitions = partitionInfos.size();

        // 根据key来进行分区, 如果key为空则抛出异常
        if (null != keyBytes && !(key instanceof String)) {
            throw new InvalidRecordException("kafka message must have key");
        }

        if (numPartitions == 1) {
            return 0;
        }

        if (key.equals("name")) {
            return numPartitions - 1;
        }

        //使用kafka默认的分配策略: 根据key获取hash值对分区数进行取余来确定发送到哪个分区
        assert keyBytes != null;
        return Math.abs(Utils.murmur2(keyBytes)) % (numPartitions - 1);
    }

    /**
     * 关闭分配器
     */
    @Override
    public void close() {

    }

    /**
     * 对分区分配器进行配置
     *
     * @param map 配置信息
     */
    @Override
    public void configure(Map<String, ?> map) {

    }

}
