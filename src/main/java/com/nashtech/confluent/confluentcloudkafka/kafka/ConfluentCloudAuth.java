package com.nashtech.confluent.confluentcloudkafka.kafka;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * MskAuth Properties.
 */
@ToString
@Getter
@Builder
public class ConfluentCloudAuth {


    /**
     * set the security Protocol - ${SECURITY_PROTOCOL}.
     */
    private String securityProtocol;

    /**
     * set the SASL Mechanism - ${SASL_MECHANISM}.
     */
    private String saslMechanism;

    /**
     * set jaas - ${JAAS_CONFIG}
     */
    private String jaasConfig;
}
