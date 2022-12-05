package ru.osokin.neilspring.firstspringrestapp.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.osokin.neilspring.firstspringrestapp.dto.SensorDTO;
import ru.osokin.neilspring.firstspringrestapp.models.Sensor;
import ru.osokin.neilspring.firstspringrestapp.repositories.SensorsRepository;
import ru.osokin.neilspring.firstspringrestapp.util.exceptions.SensorRegistrationException;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public SensorValidator(SensorsRepository sensorsRepository, ModelMapper modelMapper, ModelMapper modelMapper1) {
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper1;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = modelMapper.map(target,Sensor.class);
        Optional<Sensor> sensorToFind = sensorsRepository.findByName(sensor.getName());
        if (sensorToFind.isPresent()) throw new SensorRegistrationException("Sensor with this name already exists");
    }

    public boolean isSensorActual(Sensor sensor) {
        return sensorsRepository.findByName(sensor.getName()).isPresent();
    }
}
