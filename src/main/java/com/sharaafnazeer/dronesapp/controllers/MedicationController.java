/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.controllers;

import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.dto.MessageResponseDto;
import com.sharaafnazeer.dronesapp.services.MedicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "medications")
@Validated
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDto> saveMedication(@Valid @RequestBody MedicationDto dto) {
        MedicationDto createdMedication = medicationService.saveMedication(dto, false);
        if (createdMedication != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDto(ResponseMessages.MEDICATION_CREATED));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDto(ResponseMessages.MEDICATION_NOT_CREATED));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicationDto>> getMedications() {

        List<MedicationDto> medicationDtos = medicationService.getMedications();
        return ResponseEntity.ok(medicationDtos);
    }
}
