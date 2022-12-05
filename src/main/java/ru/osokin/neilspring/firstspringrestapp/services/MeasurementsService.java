package ru.osokin.neilspring.firstspringrestapp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.osokin.neilspring.firstspringrestapp.dto.MeasurementDTO;
import ru.osokin.neilspring.firstspringrestapp.models.Measurement;
import ru.osokin.neilspring.firstspringrestapp.models.Sensor;
import ru.osokin.neilspring.firstspringrestapp.repositories.MeasurementsRepository;
import ru.osokin.neilspring.firstspringrestapp.repositories.SensorsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsRepository sensorsRepository, ModelMapper modelMapper) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper;
    }

    public List<MeasurementDTO> getAllMeasurements() {
        return measurementsRepository.findAll().stream()
                .map(this::measurementToDto)
                .collect(Collectors.toList());
    }

    private MeasurementDTO measurementToDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @Transactional
    public void create(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);

        Optional<Sensor> sensor = sensorsRepository.findByName(measurement.getSensor().getName());
        sensor.ifPresent(measurement::setSensor);

        measurement.setCreatedAd(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }
}

