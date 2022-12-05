package ru.osokin.neilspring.firstspringrestapp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.osokin.neilspring.firstspringrestapp.dto.MeasurementDTO;
import ru.osokin.neilspring.firstspringrestapp.services.MeasurementsService;
import ru.osokin.neilspring.firstspringrestapp.util.MeasurementErrorResponse;
import ru.osokin.neilspring.firstspringrestapp.util.MeasurementValidator;
import ru.osokin.neilspring.firstspringrestapp.util.exceptions.MeasurementCreationException;
import ru.osokin.neilspring.firstspringrestapp.util.exceptions.SensorNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final MeasurementValidator validator;
    private final ModelMapper modelMapper;
    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementValidator validator, ModelMapper modelMapper) {
        this.measurementsService = measurementsService;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> getAllMeasurements() {
        return measurementsService.getAllMeasurements();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        validator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                stringBuilder.append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementCreationException(stringBuilder.toString());
        }

        measurementsService.create(measurementDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(SensorNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
