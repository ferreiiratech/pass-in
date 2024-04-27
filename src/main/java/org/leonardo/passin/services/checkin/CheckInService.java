package org.leonardo.passin.services.checkin;

import lombok.RequiredArgsConstructor;
import org.leonardo.passin.domain.attendee.Attendee;
import org.leonardo.passin.domain.checkin.CheckIn;
import org.leonardo.passin.domain.checkin.exceptions.CheckInReadyExistException;
import org.leonardo.passin.repositories.checkIn.CheckInRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;

    public void registerCheckIn(Attendee attendee) {
        this.verifyCheckInExist(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkInRepository.save(newCheckIn);
    }


    private void verifyCheckInExist(String attendeeId){
        Optional<CheckIn> checkIn = this.getCheckIn(attendeeId);

        if (checkIn.isPresent()) {
            throw new CheckInReadyExistException("Attendee already checkIn");
        }
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkInRepository.findByAttendeeId(attendeeId);
    }
}
