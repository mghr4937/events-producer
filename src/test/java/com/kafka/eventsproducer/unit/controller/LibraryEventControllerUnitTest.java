package com.kafka.eventsproducer.unit.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.eventsproducer.controller.LibraryEventController;
import com.kafka.eventsproducer.domain.Book;
import com.kafka.eventsproducer.domain.LibraryEvent;
import com.kafka.eventsproducer.producer.EventsProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryEventController.class)
@AutoConfigureMockMvc
public class LibraryEventControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventsProducer eventsProducer;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void postLibraryEvent() throws Exception {
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

        when(eventsProducer.sendLibraryEventWithTopic(isA(LibraryEvent.class))).thenReturn(null);

        //expect
        mockMvc.perform(post("/v1/libraryEvent")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void postLibraryEvent_4xx() throws Exception {
        //given
        Book book = Book.builder()
                .id(null)
                .author(null)
                .name("Kafka Using Spring Boot")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .id(null)
                .book(book)
                .build();

        String json = objectMapper.writeValueAsString(libraryEvent);
        when(eventsProducer.sendLibraryEventWithTopic(isA(LibraryEvent.class))).thenReturn(null);

        //expect
        String expectedMessage = "book.author - must not be blank , book.id - must not be null";

        mockMvc.perform(post("/v1/libraryEvent")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
        .andExpect(content().string(expectedMessage));
    }

    @Test
    void putLibraryEvent() throws Exception {
        //given
        Book book = Book.builder()
                .id(123)
                .author("Dummy")
                .name("Kafka Using Spring Boot")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .id(334)
                .book(book)
                .build();

        String json = objectMapper.writeValueAsString(libraryEvent);

        when(eventsProducer.sendLibraryEventWithTopic(isA(LibraryEvent.class))).thenReturn(null);

        //expect
        mockMvc.perform(put("/v1/libraryEvent")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateLibraryEvent_withNullLibraryEventId() throws Exception {
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

        when(eventsProducer.sendLibraryEventWithTopic(isA(LibraryEvent.class))).thenReturn(null);

        //expect
        mockMvc.perform(put("/v1/libraryEvent")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
