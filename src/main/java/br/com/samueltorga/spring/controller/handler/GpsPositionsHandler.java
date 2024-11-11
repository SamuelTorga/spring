package br.com.samueltorga.spring.controller.handler;

import br.com.samueltorga.spring.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GpsPositionsHandler {

    @KafkaListener(groupId = KafkaConfig.GROUP_ID, topics = KafkaConfig.GPS_POSITIONS_TOPIC)
    public void onMessage(ConsumerRecord<String, String> data) {
        log.info("Received message: {}", data.value());
    }
}
