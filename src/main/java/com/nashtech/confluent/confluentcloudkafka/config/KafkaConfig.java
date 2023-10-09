package com.nashtech.confluent.confluentcloudkafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuring bootstrapServer, keyDeserializer, valueDeserializer, and
 * kafka listener container factory related to kafka consumer.
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(KafkaConfig.class);

    /**
     * bootstrapServer.
     */
    @Value("${spring.kafka.properties.bootstrap.servers}")
    private String bootstrapServer;


    @Value("${spring.kafka.properties.security.protocol}")
    private String securityProtocol;

    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String jaasConfig;


    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String saslMechanism;


    /**
     * Consumer factory.
     *
     * @return the consumer factory
     */
    @Bean
    public ConsumerFactory<String, byte[]> consumerFactory() {
        final Map<String, Object> configMap = new HashMap<>();

        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapServer);
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        configMap.put("sasl.mechanism", saslMechanism);
        configMap.put("sasl.jaas.config", jaasConfig);
        configMap.put("security.protocol", securityProtocol);
        return new DefaultKafkaConsumerFactory<>(configMap);
    }

    /**
     * kafka listener container factory.
     *
     * @return the concurrent kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]>
    kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, byte[]> factory
            = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(2);
        return factory;
    }
}
