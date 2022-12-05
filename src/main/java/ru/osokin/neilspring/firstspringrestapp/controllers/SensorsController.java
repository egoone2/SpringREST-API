package ru.osokin.neilspring.firstspringrestapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.osokin.neilspring.firstspringrestapp.dto.SensorDTO;
import ru.osokin.neilspring.firstspringrestapp.services.SensorsService;
import ru.osokin.neilspring.firstspringrestapp.util.SensorErrorResponse;
import ru.osokin.neilspring.firstspringrestapp.util.SensorValidator;
import ru.osokin.neilspring.firstspringrestapp.util.exceptions.SensorRegistrationException;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final SensorValidator validator;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator validator) {
        this.sensorsService = sensorsService;
        this.validator = validator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult) {
        validator.validate(sensorDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                stringBuilder.append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorRegistrationException(stringBuilder.toString());
        }

        sensorsService.create(sensorDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(SensorRegistrationException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
