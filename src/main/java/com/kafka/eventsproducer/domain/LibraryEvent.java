package com.kafka.eventsproducer.domain;

import com.kafka.eventsproducer.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LibraryEvent {

    private Integer id;
    private Book book;

    private EventType eventType;
}
