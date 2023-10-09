package com.nashtech.confluent.confluentcloudkafka.kafka;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides the implementation of the EQProducer interface.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
@Component
public abstract class EQProducerAbs<K, V> implements EQProducer<K, V> {

    /**
     * Instantiates a logger for the EQProducerImpl using LoggerFactory.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(EQProducerAbs.class);

    /**
     * Sets the value for byte array type serializer.
     */
    private static final String BYTE_ARRAY_SERIALIZER =
            "org.apache.kafka.common.serialization.ByteArraySerializer";

    /**
     * Sets the server port for bootstrap server
     */
    private final String bootstrapServer;

    private final ConfluentCloudAuth confluentAuth;


    /**
     * Instantiates a new Eq producer abs.
     *
     * @param serverPath Kafka bootstrap server.
     */
    protected EQProducerAbs(final String serverPath,
                            final ConfluentCloudAuth confluentAuth) {
        this.bootstrapServer = serverPath;
        this.confluentAuth = confluentAuth;
    }

    /**
     * Create sender property map
     * based upon the type of the instance of a message i.e. byte[], String, ...
     * that type of map of key, Value serializer will generate.
     *
     * @return the map of the key value pair
     * @throws IllegalArgumentException if sender property is not set for the
     *                                  value-serializer
     */
    protected Map<String, Object> createSenderProp() {
        Map<String, Object> senderProps = new HashMap<>();
        senderProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                this.bootstrapServer
        );
        senderProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                EQProducerAbs.BYTE_ARRAY_SERIALIZER
        );
        senderProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                EQProducerAbs.BYTE_ARRAY_SERIALIZER
        );
        senderProps.put(
                ProducerConfig.ACKS_CONFIG,
                "all"
        );
        senderProps.put("sasl.mechanism", confluentAuth.getSaslMechanism());
        senderProps.put("sasl.jaas.config", confluentAuth.getJaasConfig());
        senderProps.put("security.protocol", confluentAuth.getSecurityProtocol());


        final boolean senderPropsSize = senderProps.size() >= 3;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Properties Initialized: {} ", senderProps);
        }
        if (senderPropsSize) {
            return senderProps;
        } else {
            throw new IllegalArgumentException("Value-serializer is required");
        }
    }

}
