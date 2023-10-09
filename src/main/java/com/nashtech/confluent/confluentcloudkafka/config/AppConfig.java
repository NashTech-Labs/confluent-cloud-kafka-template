package com.nashtech.confluent.confluentcloudkafka.config;

import com.nashtech.confluent.confluentcloudkafka.kafka.ConfluentCloudAuth;
import com.nashtech.confluent.confluentcloudkafka.kafka.EQProducer;
import com.nashtech.confluent.confluentcloudkafka.kafka.EQProducerImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
public class AppConfig {

    private static ConfluentCloudAuth confluentCloudAuth;

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

    @Bean
    public String getServer() {
        return bootstrapServer;
    }


    /**
     * For producer need only one time confluent authentication.
     * And, it has no adverse effect in multithreaded env.
     *
     * @return Producer instance.
     */
    @Bean
    public EQProducer<byte[], byte[]> getEqProducer() {

        if (AppConfig.confluentCloudAuth == null) {

                AppConfig.confluentCloudAuth = ConfluentCloudAuth.builder()
                        .securityProtocol(securityProtocol)
                        .jaasConfig(jaasConfig)
                        .saslMechanism(saslMechanism)
                        .build();

            }

        return new EQProducerImpl(getServer(), confluentCloudAuth);
    }

}
