package br.com.samueltorga.spring.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaConfig {

    public static final String GPS_POSITIONS_TOPIC = "gps_positions";
    public static final String GROUP_ID = "gps_positions_group";

    @Bean
    AdminClient adminClient(KafkaAdmin kafkaAdmin) {
        return AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(GPS_POSITIONS_TOPIC)
                .partitions(3)
                .replicas(3)
                .build();
    }

}
