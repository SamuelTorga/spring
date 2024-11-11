package br.com.samueltorga.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static br.com.samueltorga.spring.config.KafkaConfig.GPS_POSITIONS_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void registerGps(String message) {
        kafkaTemplate.send(GPS_POSITIONS_TOPIC, message)
                .thenAcceptAsync(
                        result -> log.trace("Sent message=[{}] with offset=[{}]", message, result)
                );
    }

}
