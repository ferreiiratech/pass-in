package org.leonardo.passin.services.attendee;

import lombok.RequiredArgsConstructor;
import org.leonardo.passin.domain.attendee.Attendee;
import org.leonardo.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import org.leonardo.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import org.leonardo.passin.domain.checkin.CheckIn;
import org.leonardo.passin.dto.attendee.AttendeeBadgeResponseDTO;
import org.leonardo.passin.dto.attendee.AttendeeDetails;
import org.leonardo.passin.dto.attendee.AttendeeListResponseDTO;
import org.leonardo.passin.dto.attendee.AttendeeBadgeDTO;
import org.leonardo.passin.repositories.attendee.AttendeeRepository;
import org.leonardo.passin.repositories.checkIn.CheckInRepository;
import org.leonardo.passin.services.checkin.CheckInService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {

        return this.attendeeRepository.findAllByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventAttendees(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());

            LocalDateTime checkedInAt = checkIn.map(CheckIn::getCreatedAt).orElse(null);

            return new AttendeeDetails(
                    attendee.getId(),
                    attendee.getName(),
                    attendee.getEmail(),
                    attendee.getCreatedAt(),
                    checkedInAt
            );
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

        if (isAttendeeRegistered.isPresent()) {
            throw new AttendeeAlreadyExistException("Attendee is already registered");
        }
    }

    public Attendee registerAttendee(Attendee newAttendee) {
        return this.attendeeRepository.save(newAttendee);
    }

    public void checkInAttendee(String attendeeId){
        Attendee attendee = this.getAttendee(attendeeId);

        this.checkInService.registerCheckIn(attendee);
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        Attendee attendee = getAttendee(attendeeId);

        URI uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri();

        AttendeeBadgeDTO badgeDTO = new AttendeeBadgeDTO(
                attendee.getName(),
                attendee.getEmail(),
                uri.toString(),
                attendee.getEvent().getId()
        );

        return new AttendeeBadgeResponseDTO(badgeDTO);
    }

    private Attendee getAttendee(String attendeeId) {
        Optional<Attendee> attendee = this.attendeeRepository.findById(attendeeId);

        if(attendee.isEmpty()) {
            throw new AttendeeNotFoundException("Attendee not found");
        }

        return attendee.get();
    }
}
