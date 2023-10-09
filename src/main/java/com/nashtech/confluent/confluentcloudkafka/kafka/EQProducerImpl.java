package com.nashtech.confluent.confluentcloudkafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This class provides the implementation for the generic producer
 *
 */
public class EQProducerImpl extends EQProducerAbs<byte[], byte[]> {
    /**
     * Instantiates a logger for the EQProducerImpl using LoggerFactory.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(EQProducerImpl.class);

    private static KafkaTemplate<byte[], byte[]> template = null;

    private static DefaultKafkaProducerFactory<byte[], byte[]> pf = null;

    /**
     * Instantiates a new Eq producer.
     *
     * @param serverPath the server path
     */
    public EQProducerImpl(final String serverPath,
                          final ConfluentCloudAuth confluentCloudAuth) {
        super(serverPath, confluentCloudAuth);
    }

    /**
     * Responsible for calling the send method of Kafka Template
     * it receives topicName, key, message, and the sender property map
     *
     * @param topicName   the topic name where me message to be sent
     * @param key         the key related to the message
     * @param message     the message is the required protos
     * @param senderProps the configuration related to kafka producer
     * @return the listenable future
     */
    private CompletableFuture<SendResult<byte[], byte[]>> kafkaProducerFactory(
            final String topicName, final byte[] key, final byte[] message,
            final Map<String, Object> senderProps) {
        //kafka template
        if (template == null || pf == null) {
            pf = new DefaultKafkaProducerFactory<>(senderProps); //NOSONAR
            template = new KafkaTemplate<>(pf); //NOSONAR
        }
        final CompletableFuture<SendResult<byte[], byte[]>> future = template
                .send(topicName, key, message);
        //ensure all previously sent messages have actually completed.
        template.flush();
        return future;
    }

    /**
     * Sends topic name, key and message to kafka producer.
     *
     * @param topicName the topic name where me message to be sent
     * @param key       the key related to the message
     * @param value     the message is the required protos
     * @return callbackSuccessOrFailure
     */
    @Override
    public CompletableFuture<SendResult<byte[], byte[]>> send(
            final String topicName, final byte[] key, final byte[] value) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Topic Name: {}, Key: {}, Message: {}",
                    topicName, key, value);
        }
        if (topicName != null && key != null && value != null) {

           return kafkaProducerFactory(topicName, key, value,
                            createSenderProp());
        }
        throw new IllegalArgumentException("Arguments can not be null");
    }

}
