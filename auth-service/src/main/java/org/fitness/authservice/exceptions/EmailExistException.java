package org.fitness.authservice.exceptions;

public class EmailExistException extends Exception{
    public EmailExistException(String message) {
        super(message);
    }

    public EmailExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
