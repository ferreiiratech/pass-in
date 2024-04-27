package org.leonardo.passin.config;

import org.leonardo.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import org.leonardo.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import org.leonardo.passin.domain.checkin.exceptions.CheckInReadyExistException;
import org.leonardo.passin.domain.events.exceptions.EventExistException;
import org.leonardo.passin.domain.events.exceptions.EventFullException;
import org.leonardo.passin.domain.events.exceptions.EventNotFoundException;
import org.leonardo.passin.dto.exceptions.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleExceptionNotFound(EventNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAttendeeNotFound(AttendeeNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleAttendeeAlreadyExist(AttendeeAlreadyExistException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(CheckInReadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleCheckInAlreadyExist(CheckInReadyExistException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(EventExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventExist(EventExistException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }
}
