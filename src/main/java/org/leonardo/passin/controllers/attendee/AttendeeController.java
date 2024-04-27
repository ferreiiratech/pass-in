package org.leonardo.passin.controllers.attendee;

import lombok.RequiredArgsConstructor;
import org.leonardo.passin.dto.attendee.AttendeeBadgeResponseDTO;
import org.leonardo.passin.services.attendee.AttendeeService;
import org.leonardo.passin.services.checkin.CheckInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendees")
public class AttendeeController {
    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeBadgeResponseDTO attendeeBadgeResponseDTO = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);

        return ResponseEntity.status(HttpStatus.OK).body(attendeeBadgeResponseDTO);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<URI> registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.attendeeService.checkInAttendee(attendeeId);

        URI uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }

}
