/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.controllers;

import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import com.sharaafnazeer.dronesapp.dto.*;
import com.sharaafnazeer.dronesapp.services.DroneService;
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
@RequestMapping(value = "/api/v1/drones")
@Validated
public class DroneController {
    private final DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDto> registerDrone(@Valid @RequestBody DroneDto dto) {

        DroneDto createdDrone = droneService.registerDrone(dto);
        if (createdDrone != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDto(ResponseMessages.DRONE_CREATED));
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDto(ResponseMessages.DRONE_NOT_CREATED));
    }

    @GetMapping(value = "/available", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DroneDto>> getAvailableDrones() {

        List<DroneDto> droneDtoList = droneService.getAvailableDrones();
        return ResponseEntity.ok(droneDtoList);
    }

    @GetMapping(value = "{serialNumber}/battery", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DroneBatteryDto> getDroneBattery(@PathVariable("serialNumber") String serialNumber) {

        DroneBatteryDto droneBatteryDto = droneService.checkDroneBattery(serialNumber);
        return new ResponseEntity<>(droneBatteryDto, HttpStatus.OK);
    }

    @GetMapping(value = "{serialNumber}/medications", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicationDto>> getDroneMedications(@PathVariable("serialNumber") String serialNumber) {
        List<MedicationDto> medicationDtos = droneService.getMedicationsByDrone(serialNumber);
        return new ResponseEntity<>(medicationDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/load", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDto> loadDrone(@Valid @RequestBody LoadDroneDto dto) {

        droneService.loadDrone(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDto(ResponseMessages.DRONE_LOADED));
    }
}
