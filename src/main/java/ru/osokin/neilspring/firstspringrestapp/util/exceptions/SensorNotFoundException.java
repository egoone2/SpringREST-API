package ru.osokin.neilspring.firstspringrestapp.util.exceptions;

public class SensorNotFoundException extends RuntimeException {
    public SensorNotFoundException(String message) {
        super(message);
    }
}
