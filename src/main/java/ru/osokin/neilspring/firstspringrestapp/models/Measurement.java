package ru.osokin.neilspring.firstspringrestapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Measurement")
@ToString(of = {"value", "raining", "sensor", "createdAt"})
public class Measurement {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = -100, message = "Temperature should be heater than -100 degrees Celsius")
    @Max(value = 100, message = "Temperature should be colder than 100 degrees Celsius")
    @Column(name = "value")
    @NotNull(message = "Value should not be empty")
    private double value;

    @Column(name = "raining")
    @NotNull(message = "Raining parameter should not be empty")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @NotNull(message = "Sensor should not be empty")
    private Sensor sensor;
    @Column(name = "created_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime createdAd;

    public Measurement(double value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }
}
