package com.sharaafnazeer.dronesapp.services.impl;

import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import com.sharaafnazeer.dronesapp.dto.DroneBatteryDto;
import com.sharaafnazeer.dronesapp.dto.DroneDto;
import com.sharaafnazeer.dronesapp.dto.LoadDroneDto;
import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.entities.Drone;
import com.sharaafnazeer.dronesapp.enums.DroneModel;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import com.sharaafnazeer.dronesapp.exceptions.DroneException;
import com.sharaafnazeer.dronesapp.mappers.DroneMapper;
import com.sharaafnazeer.dronesapp.repos.DroneRepository;
import com.sharaafnazeer.dronesapp.services.MedicationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Component
class DroneServiceImplTest {
    @Mock
    private DroneRepository droneRepository;
    @Mock
    private MedicationService medicationService;
    @InjectMocks
    private DroneServiceImpl droneService;
    @Spy
    DroneMapper mapper = Mappers.getMapper(DroneMapper.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerDrone() {
        when(droneRepository.save(any(Drone.class))).thenReturn(getMockDrone());
        DroneDto droneDto = droneService.registerDrone(getMockDroneDto());
        assertNotNull(droneDto);
        assertEquals("ABC123", droneDto.getSerialNumber());
    }

    @Test
    void getDrone() {
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDrone());
        DroneDto droneDto = droneService.getDrone("ABC123");
        assertNotNull(droneDto);
        assertEquals("ABC123", droneDto.getSerialNumber());
    }

    @Test
    void getDrones() {
        when(droneRepository.findAll()).thenReturn(List.of(getMockDrone()));
        List<DroneDto> droneDtos = droneService.getDrones();
        assertNotNull(droneDtos);
        assertEquals("ABC123", droneDtos.get(0).getSerialNumber());
    }

    @Test
    void getAvailableDrones() {
        List<DroneState> status = new ArrayList<>();
        status.add(DroneState.IDLE);
        status.add(DroneState.LOADING);
        when(droneRepository.findByStateIn(status)).thenReturn(List.of(getMockDrone()));
        List<DroneDto> droneDtos = droneService.getAvailableDrones();
        assertNotNull(droneDtos);
        assertEquals("ABC123", droneDtos.get(0).getSerialNumber());
    }

    @Test
    void checkDroneBattery() {
        // Success
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDrone());
        DroneBatteryDto droneDto = droneService.checkDroneBattery("ABC123");
        assertNotNull(droneDto);
        assertEquals("ABC123", droneDto.getSerialNumber());

        // Fail - Drone not available

        when(droneRepository.findBySerialNumber(anyString())).thenReturn(null);
        assertThrows(DroneException.class,
                () -> droneService.checkDroneBattery("ABC123"),
                ResponseMessages.DRONE_NOT_FOUND);
    }

    @Test
    void loadDrone() {

        // Load Drone Success
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDrone());
        when(medicationService.getMedicationByCode(anyString())).thenReturn(getMockMedicationDto());
        droneService.loadDrone(getMockMedicationLoad());


        // Load Drone - Drone Not Available
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(null);
        assertThrows(DroneException.class,
                () -> droneService.loadDrone(getMockMedicationLoad()),
                ResponseMessages.DRONE_NOT_FOUND);

        // Load Drone - Battery Limit Error
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDroneLowBattery());
        assertThrows(DroneException.class,
                () -> droneService.loadDrone(getMockMedicationLoad()),
                ResponseMessages.DRONE_BATTERY_LOW);

        // Load Drone - Not Able to Load
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDroneCantLoad());
        assertThrows(DroneException.class,
                () -> droneService.loadDrone(getMockMedicationLoad()),
                ResponseMessages.DRONE_LOADING_STATE_EXCEEDED);


        // Load Drone - Medication Not Available
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDrone());
        when(medicationService.getMedicationByCode(anyString())).thenReturn(null);
        assertThrows(DroneException.class,
                () -> droneService.loadDrone(getMockMedicationLoad()),
                ResponseMessages.MEDICATION_NOT_FOUND);

        // Load Drone - Weight Exceeded

        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDrone());
        when(medicationService.getMedicationByCode(anyString())).thenReturn(getMockMedicationExceededDto());
        assertThrows(DroneException.class,
                () -> droneService.loadDrone(getMockMedicationLoad()),
                ResponseMessages.DRONE_MAX_WEIGHT_EXCEEDED);

        // Load Drone - Weight Equal and Loaded

        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDrone());
        when(medicationService.getMedicationByCode(anyString())).thenReturn(getMockMedicationEqualDto());
        droneService.loadDrone(getMockMedicationLoad());

    }

    @Test
    void getMedicationsByDrone() {

        // Medications By Drone Success

        when(droneRepository.findBySerialNumber(anyString())).thenReturn(getMockDrone());
        when(medicationService.getMedicationsByDrone(any(Drone.class))).thenReturn(List.of(getMockMedicationDto()));
        List<MedicationDto> medicationDtos = droneService.getMedicationsByDrone("ABC123");
        assertNotNull(medicationDtos);
        assertEquals("MED1234", medicationDtos.get(0).getCode());

        // Medications By Drone - Drone Not available
        when(droneRepository.findBySerialNumber(anyString())).thenReturn(null);
        assertThrows(DroneException.class,
                () -> droneService.getMedicationsByDrone("ABC123"),
                ResponseMessages.DRONE_NOT_FOUND);
    }

    Drone getMockDrone() {
        Drone droneEntity = new Drone();
        droneEntity.setSerialNumber("ABC123");
        droneEntity.setState(DroneState.IDLE);
        droneEntity.setCurrentWeight(0.0);
        droneEntity.setMaxWeight(500.0);
        droneEntity.setBatteryLife(100);
        droneEntity.setModel(DroneModel.Cruiserweight);
        return droneEntity;
    }

    Drone getMockDroneLowBattery() {
        Drone droneEntity = new Drone();
        droneEntity.setSerialNumber("ABC123");
        droneEntity.setState(DroneState.IDLE);
        droneEntity.setCurrentWeight(0.0);
        droneEntity.setMaxWeight(500.0);
        droneEntity.setBatteryLife(18);
        droneEntity.setModel(DroneModel.Cruiserweight);
        return droneEntity;
    }

    Drone getMockDroneCantLoad() {
        Drone droneEntity = new Drone();
        droneEntity.setSerialNumber("ABC123");
        droneEntity.setState(DroneState.LOADED);
        droneEntity.setCurrentWeight(0.0);
        droneEntity.setMaxWeight(500.0);
        droneEntity.setBatteryLife(60);
        droneEntity.setModel(DroneModel.Cruiserweight);
        return droneEntity;
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

    MedicationDto getMockMedicationDto() {
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setCode("MED1234");
        medicationDto.setName("Med1");
        medicationDto.setImage("");
        medicationDto.setWeight(100.0);
        medicationDto.setDrone(getMockDroneDto());
        return medicationDto;
    }

    MedicationDto getMockMedicationExceededDto() {
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setCode("MED1234");
        medicationDto.setName("Med1");
        medicationDto.setImage("");
        medicationDto.setWeight(600.0);
        medicationDto.setDrone(getMockDroneDto());
        return medicationDto;
    }

    MedicationDto getMockMedicationEqualDto() {
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setCode("MED1234");
        medicationDto.setName("Med1");
        medicationDto.setImage("");
        medicationDto.setWeight(500.0);
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