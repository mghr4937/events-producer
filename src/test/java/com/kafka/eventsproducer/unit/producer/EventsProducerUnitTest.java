package com.kafka.eventsproducer.unit.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.eventsproducer.domain.Book;
import com.kafka.eventsproducer.domain.LibraryEvent;
import com.kafka.eventsproducer.producer.EventsProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventsProducerUnitTest {

    @Mock
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    EventsProducer eventsProducer;

    @Test
    void sendLibraryEventWithTopic_failure() throws JsonProcessingException, ExecutionException, InterruptedException {
        //given
        Book book = Book.builder()
                .id(123)
                .author("Dummy")
                .name("Kafka Using Spring Boot")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .id(null)
                .book(book)
                .build();

        SettableListenableFuture future = new SettableListenableFuture();
        future.setException(new RuntimeException("Exception calling kafka"));

        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);
        //when

        assertThrows(Exception.class,()->  eventsProducer.sendLibraryEventWithTopic(libraryEvent).get());
        //then

    }

    @Test
    void sendLibraryEventWithTopic_success() throws JsonProcessingException, ExecutionException, InterruptedException {
        //given
        Book book = Book.builder()
                .id(123)
                .author("Dummy")
                .name("Kafka Using Spring Boot")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .id(null)
                .book(book)
                .build();
        String json = objectMapper.writeValueAsString(libraryEvent);
        ProducerRecord<Integer, String> producerRecord = new ProducerRecord("library-events", libraryEvent.getId(), json);
        RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("library-events", 1), 1,
                1, 342, System.currentTimeMillis(), 1,2  );


        SendResult<Integer, String> sendResult = new SendResult<Integer, String>(producerRecord, recordMetadata);

        SettableListenableFuture future = new SettableListenableFuture();
        future.set(sendResult);

        when(kafkaTemplate.send(isA(ProducerRecord.class))).thenReturn(future);
        //when

         ListenableFuture<SendResult<Integer,String>> listenableFuture = eventsProducer.sendLibraryEventWithTopic(libraryEvent);

         //then
        SendResult<Integer,String> sendResult1= listenableFuture.get();
        assert sendResult1.getRecordMetadata().partition() == 1;
    }
}
