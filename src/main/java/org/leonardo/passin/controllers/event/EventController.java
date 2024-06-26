package org.leonardo.passin.controllers.event;

import lombok.RequiredArgsConstructor;
import org.leonardo.passin.dto.attendee.AttendeeIdDTO;
import org.leonardo.passin.dto.attendee.AttendeeListResponseDTO;
import org.leonardo.passin.dto.attendee.AttendeeRequestDTO;
import org.leonardo.passin.dto.event.EventIdDTO;
import org.leonardo.passin.dto.event.EventRequestDTO;
import org.leonardo.passin.dto.event.EventResponseDTO;
import org.leonardo.passin.services.attendee.AttendeeService;
import org.leonardo.passin.services.event.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
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

        URI uri = uriBuilder.path("/events/{eventId}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);

    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO attendeeRequestDTO, UriComponentsBuilder uriBuilder){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, attendeeRequestDTO);

        URI uri = uriBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);

    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String eventId){
        AttendeeListResponseDTO attendeeListResponseDTO = this.attendeeService.getEventAttendees(eventId);

        return ResponseEntity.status(HttpStatus.OK).body(attendeeListResponseDTO);
    }




}
