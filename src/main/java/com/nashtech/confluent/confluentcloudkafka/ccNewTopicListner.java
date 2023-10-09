package com.nashtech.confluent.confluentcloudkafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
public class ccNewTopicListner {

    private static final Logger LOGGER =
        LoggerFactory.getLogger(ccNewTopicListner.class);

    @KafkaListener(topics = "sushant_topic",
        groupId = "group-id-1",
        containerFactory = "kafkaListenerContainerFactory")
    public void fromKafka(final ConsumerRecord<byte[], byte[]> records) {

        String str = new String(records.value(), StandardCharsets.UTF_8);

        LOGGER.info("2222222222222 " + str);

    }
}
