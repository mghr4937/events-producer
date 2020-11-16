package com.kafka.eventsproducer.domain;

import com.kafka.eventsproducer.domain.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LibraryEvent {

    private Integer id;

    private EventType eventType;

    @NotNull
    @Valid
    private Book book;


}
