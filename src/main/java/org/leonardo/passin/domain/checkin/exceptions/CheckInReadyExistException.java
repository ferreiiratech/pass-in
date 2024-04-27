package org.leonardo.passin.domain.checkin.exceptions;

public class CheckInReadyExistException extends RuntimeException {
    public CheckInReadyExistException(String message) {
        super(message);
    }
}
