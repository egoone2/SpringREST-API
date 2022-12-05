package ru.osokin.neilspring.firstspringrestapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.osokin.neilspring.firstspringrestapp.models.Sensor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {
    private double value;
    private boolean raining;
    private Sensor sensor;
}
