package org.leonardo.passin.repositories.attendee;

import org.leonardo.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
    List<Attendee> findAllByEventId(String eventId);
}
