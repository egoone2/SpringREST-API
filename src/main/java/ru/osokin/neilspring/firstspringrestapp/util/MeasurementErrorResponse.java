package ru.osokin.neilspring.firstspringrestapp.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MeasurementErrorResponse {
    private String message;
    private long timestamp;
}
