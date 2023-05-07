package com.sharaafnazeer.dronesapp.services.impl;

import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.entities.Drone;
import com.sharaafnazeer.dronesapp.entities.Medication;
import com.sharaafnazeer.dronesapp.enums.DroneModel;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import com.sharaafnazeer.dronesapp.mappers.MedicationMapper;
import com.sharaafnazeer.dronesapp.repos.MedicationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MedicationServiceImplTest {

    @InjectMocks
    MedicationServiceImpl medicationService;
    @Mock
    MedicationRepository medicationRepository;
    @Spy
    MedicationMapper mapper = Mappers.getMapper(MedicationMapper.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveMedication() {
        when(medicationRepository.save(any(Medication.class))).thenReturn(getMedicationMock());
        MedicationDto medicationDto = medicationService.saveMedication(getMockMedicationDto());
        assertNotNull(medicationDto);
        assertEquals("MED", medicationDto.getCode());
    }

    @Test
    void getMedications() {
        when(medicationRepository.findAll()).thenReturn(List.of(getMedicationMock()));
        List<MedicationDto> medicationDtos = medicationService.getMedications();
        assertNotNull(medicationDtos);
        assertEquals(1, medicationDtos.size());
        assertEquals("MED", medicationDtos.get(0).getCode());
    }

    @Test
    void getMedicationByCode() {
        when(medicationRepository.findByCode(anyString())).thenReturn(getMedicationMock());
        MedicationDto medicationDto = medicationService.getMedicationByCode("MED");
        assertNotNull(medicationDto);
        assertEquals("MED", medicationDto.getCode());
    }

    @Test
    void getMedicationsByDrone() {
        when(medicationRepository.findByDrone(any(Drone.class))).thenReturn(List.of(getMedicationMock()));
        List<MedicationDto> medicationDtos = medicationService.getMedicationsByDrone(getMockDrone());
        assertNotNull(medicationDtos);
        assertEquals(1, medicationDtos.size());
        assertEquals("MED", medicationDtos.get(0).getCode());
    }

    Medication getMedicationMock() {
        Medication medication = new Medication();
        medication.setCode("MED");
        medication.setName("Med1");
        medication.setImage("");
        medication.setWeight(100.0);
        medication.setDrone(getMockDrone());
        return medication;
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

    MedicationDto getMockMedicationDto() {
        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setCode("MED");
        medicationDto.setName("Med1");
        medicationDto.setImage("");
        medicationDto.setWeight(100.0);
        return medicationDto;
    }

}