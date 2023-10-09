package com.nashtech.confluent.confluentcloudkafka.kafka;

import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

/**
 * EQProducer provides a method of type ListenableFuture to send
 * the topic name, key and message.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public interface EQProducer<K, V> {

    /**
     * Sends the topic name, key and value to the producer.
     *
     * @param topicName the topic name
     * @param key       the key
     * @param value     the value
     * @return the listenable future
     */
    CompletableFuture<SendResult<K, V>> send(String topicName,
                                             K key,
                                             V value);
}
