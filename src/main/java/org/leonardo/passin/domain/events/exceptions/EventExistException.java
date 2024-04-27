package org.leonardo.passin.domain.events.exceptions;

public class EventExistException extends RuntimeException {
    public EventExistException(String message) {
        super(message);
    }
}