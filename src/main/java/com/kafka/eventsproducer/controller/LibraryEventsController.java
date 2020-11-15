package com.kafka.eventsproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kafka.eventsproducer.domain.LibraryEvent;
import com.kafka.eventsproducer.domain.enums.EventType;
import com.kafka.eventsproducer.producer.EventsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class LibraryEventsController {

    @Autowired
    EventsProducer eventsProducer;

    @PostMapping("/v1/libraryEvent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException {

          libraryEvent.setEventType(EventType.NEW);
//        invoke kafka producer
//        ASYNC method
//        eventsProducer.sendLibraryEvent(libraryEvent);

//        SYNC method
//        SendResult<Integer, String> sendResult = eventsProducer.sendLibraryEventSync(libraryEvent);
//        log.info("SendResult is {}", sendResult);

//        With ProducerRecord - Topic
        eventsProducer.sendLibraryEventWithTopic(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }


}
