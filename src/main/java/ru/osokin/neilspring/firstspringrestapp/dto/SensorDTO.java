package ru.osokin.neilspring.firstspringrestapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SensorDTO {
    @NotEmpty
    @Size(min = 3, max = 30, message = "Sensor's name should be between 3 and 30 characters")
    private String name;
}
