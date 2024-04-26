package org.leonardo.passin.services;

import lombok.RequiredArgsConstructor;
import org.leonardo.passin.domain.attendee.Attendee;
import org.leonardo.passin.domain.checkin.CheckIn;
import org.leonardo.passin.dto.attendee.AttendeeDetails;
import org.leonardo.passin.dto.attendee.AttendeeListResponseDTO;
import org.leonardo.passin.repositories.AttendeeRepository;
import org.leonardo.passin.repositories.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {

        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventAttendees(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());

            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;

            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();


        return new AttendeeListResponseDTO(attendeeDetailsList);
    }
}
