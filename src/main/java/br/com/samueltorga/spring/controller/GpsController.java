package br.com.samueltorga.spring.controller;

import br.com.samueltorga.spring.service.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/gps")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GpsController {

    private final KafkaService kafkaService;

    @PostMapping()
    public ResponseEntity<Void> getUserById(@RequestBody String message) {
        kafkaService.registerGps(message);
        return ResponseEntity.accepted().build();
    }

}
