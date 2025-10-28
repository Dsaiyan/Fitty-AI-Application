package org.fitness.activityservice.exceptions;

public class UserNotMatchException extends RuntimeException {
    public UserNotMatchException(String message) {
        super(message);
    }
}