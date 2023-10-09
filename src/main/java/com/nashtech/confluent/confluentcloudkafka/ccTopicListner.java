package com.nashtech.confluent.confluentcloudkafka;

import com.nashtech.confluent.confluentcloudkafka.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
public class ccTopicListner {

    @Autowired
    AppConfig appConfig;

    private static final Logger LOGGER =
        LoggerFactory.getLogger(ccTopicListner.class);

    @KafkaListener(topics = "test_topic",
        groupId = "group-id-1",
        containerFactory = "kafkaListenerContainerFactory")
    public void fromKafka(final ConsumerRecord<byte[], byte[]> records) {

        String str = new String(records.value(), StandardCharsets.UTF_8);

        LOGGER.info("11111111111111 " + str);
        appConfig.getEqProducer().send("sushant_topic", records.key(), records.value());

    }
}
