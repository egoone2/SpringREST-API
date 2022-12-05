package ru.osokin.neilspring.firstspringrestapp.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.osokin.neilspring.firstspringrestapp.dto.MeasurementDTO;
import ru.osokin.neilspring.firstspringrestapp.models.Measurement;
import ru.osokin.neilspring.firstspringrestapp.repositories.MeasurementsRepository;
import ru.osokin.neilspring.firstspringrestapp.util.exceptions.SensorNotFoundException;

@Component
public class MeasurementValidator implements Validator {
    private final MeasurementsRepository measurementsRepository;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public MeasurementValidator(MeasurementsRepository measurementsRepository, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.measurementsRepository = measurementsRepository;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeasurementDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = modelMapper.map(target, Measurement.class);
        if (!sensorValidator.isSensorActual(measurement.getSensor()))
            throw new SensorNotFoundException("There is no sensor with such name");
    }
}
