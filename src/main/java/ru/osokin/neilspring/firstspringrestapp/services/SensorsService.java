package ru.osokin.neilspring.firstspringrestapp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osokin.neilspring.firstspringrestapp.dto.SensorDTO;
import ru.osokin.neilspring.firstspringrestapp.models.Sensor;
import ru.osokin.neilspring.firstspringrestapp.repositories.SensorsRepository;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository, ModelMapper modelMapper) {
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void create(SensorDTO sensorDTO) {
        sensorsRepository.save(modelMapper.map(sensorDTO, Sensor.class));
    }
}
