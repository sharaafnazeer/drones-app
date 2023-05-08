package com.sharaafnazeer.dronesapp.controllers;

import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import com.sharaafnazeer.dronesapp.dto.*;
import com.sharaafnazeer.dronesapp.enums.DroneModel;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import com.sharaafnazeer.dronesapp.services.DroneService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class DroneControllerTest {
    @InjectMocks
    DroneController droneController;
    @Mock
    DroneService droneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerDrone() {
        // Success
        when(droneService.registerDrone(any(DroneDto.class))).thenReturn(getMockDroneDto());
        ResponseEntity<MessageResponseDto> responseDto = droneController.registerDrone(getMockDroneDto());
        assertNotNull(responseDto.getBody());
        assertEquals(ResponseMessages.DRONE_CREATED, Objects.requireNonNull(responseDto.getBody()).getMessage());

        // Failed
        when(droneService.registerDrone(any(DroneDto.class))).thenReturn(null);
        ResponseEntity<MessageResponseDto> responseNullDto = droneController.registerDrone(getMockDroneDto());
        assertEquals(HttpStatus.BAD_REQUEST, responseNullDto.getStatusCode());
    }

    @Test
    void getAvailableDrones() {
        when(droneService.getAvailableDrones()).thenReturn(List.of(getMockDroneDto()));
        ResponseEntity<List<DroneDto>> droneDtoList = droneController.getAvailableDrones();
        assertNotNull(droneDtoList.getBody());
        assertEquals("ABC123", Objects.requireNonNull(droneDtoList.getBody()).get(0).getSerialNumber());
    }

    @Test
    void getDroneBattery() {
        // Success
        when(droneService.checkDroneBattery(anyString())).thenReturn(getMockDroneBatteryDto());
        ResponseEntity<DroneBatteryDto> droneDto = droneController.getDroneBattery("ABC123");
        assertNotNull(droneDto.getBody());
        assertEquals("ABC123", droneDto.getBody().getSerialNumber());

        // Drone battery NULL
        when(droneService.checkDroneBattery(anyString())).thenReturn(null);

        ResponseEntity<DroneBatteryDto> droneNullDto = droneController.getDroneBattery("ABC123");
        assertNull(droneNullDto.getBody());
    }

    @Test
    void getDroneMedications() {
        when(droneService.getMedicationsByDrone(anyString())).thenReturn(List.of(getMockMedicationDto()));
        ResponseEntity<List<MedicationDto>> medicationDtoList = droneController.getDroneMedications("ABC123");
        assertNotNull(medicationDtoList.getBody());
        assertEquals("MED1234", Objects.requireNonNull(medicationDtoList.getBody()).get(0).getCode());
    }

    @Test
    void loadDrone() {
        when(droneService.loadDrone(getMockMedicationLoad())).thenReturn(getMockDroneDto());
        ResponseEntity<MessageResponseDto> responseDto = droneController.loadDrone(getMockMedicationLoad());
        assertNotNull(responseDto.getBody());
        assertEquals(ResponseMessages.DRONE_LOADED, Objects.requireNonNull(responseDto.getBody()).getMessage());
    }

    DroneDto getMockDroneDto() {
        DroneDto droneEntity = new DroneDto();
        droneEntity.setSerialNumber("ABC123");
        droneEntity.setState(DroneState.IDLE);
        droneEntity.setMaxWeight(500.0);
        droneEntity.setBatteryLife(100);
        droneEntity.setModel(DroneModel.Cruiserweight);
        return droneEntity;
    }

    DroneBatteryDto getMockDroneBatteryDto() {
        DroneBatteryDto droneEntity = new DroneBatteryDto();
        droneEntity.setSerialNumber("ABC123");
        droneEntity.setBatteryLife(100);
        return droneEntity;
    }

    MedicationDto getMockMedicationDto() {
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setCode("MED1234");
        medicationDto.setName("Med1");
        medicationDto.setImage("");
        medicationDto.setWeight(100.0);
        medicationDto.setDrone(getMockDroneDto());
        return medicationDto;
    }

    LoadDroneDto getMockMedicationLoad() {
        LoadDroneDto loadDroneDto = new LoadDroneDto();
        loadDroneDto.setDroneSerial("ABC123");
        loadDroneDto.setMedicationCodes(List.of("MED1234"));

        return loadDroneDto;
    }
}