package org.leonardo.passin.controllers;

import lombok.RequiredArgsConstructor;
import org.leonardo.passin.dto.attendee.AttendeeListResponseDTO;
import org.leonardo.passin.dto.event.EventIdDTO;
import org.leonardo.passin.dto.event.EventRequestDTO;
import org.leonardo.passin.dto.event.EventResponseDTO;
import org.leonardo.passin.services.AttendeeService;
import org.leonardo.passin.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO eventResponseDTO = this.eventService.getEventDetail(eventId);


        return ResponseEntity.ok(eventResponseDTO);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO eventRequestDTO, UriComponentsBuilder uriBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(eventRequestDTO);

        var uri = uriBuilder.path("/events/{eventId}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);

    }



    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String eventId){
        AttendeeListResponseDTO attendeeListResponseDTO = this.attendeeService.getEventAttendees(eventId);


        return ResponseEntity.ok(attendeeListResponseDTO);
    }


}
