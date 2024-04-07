package org.leonardo.passin.dto.event;

import lombok.Getter;
import org.leonardo.passin.domain.events.Event;

@Getter
public class EventResponseDTO {
    EventDetailDTO eventDetail;

    public EventResponseDTO(Event event, Integer numberOfAttendees) {
        this.eventDetail = new EventDetailDTO(event.getId(), event.getTitle(), event.getSlug(), event.getMaximumAttendees(), numberOfAttendees);
    }
}
