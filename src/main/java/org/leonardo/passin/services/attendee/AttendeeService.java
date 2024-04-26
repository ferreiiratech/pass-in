package org.leonardo.passin.services.attendee;

import lombok.RequiredArgsConstructor;
import org.leonardo.passin.domain.attendee.Attendee;
import org.leonardo.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import org.leonardo.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import org.leonardo.passin.domain.checkin.CheckIn;
import org.leonardo.passin.dto.attendee.AttendeeDetails;
import org.leonardo.passin.dto.attendee.AttendeeListResponseDTO;
import org.leonardo.passin.repositories.attendee.AttendeeRepository;
import org.leonardo.passin.repositories.checkIn.CheckInRepository;
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

        return this.attendeeRepository.findAllByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventAttendees(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());

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

    public void getAttendeeBadge(String attendeeId){
        Optional<Attendee> attendee = attendeeRepository.findById(attendeeId);

        if(attendee.isEmpty()){
            throw new AttendeeNotFoundException("Attendee not found with id " + attendeeId);
        }


    }
}
