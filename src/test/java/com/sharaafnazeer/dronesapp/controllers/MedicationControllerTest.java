package com.sharaafnazeer.dronesapp.controllers;

import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.dto.MessageResponseDto;
import com.sharaafnazeer.dronesapp.services.MedicationService;
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
import static org.mockito.Mockito.when;

class MedicationControllerTest {

    @InjectMocks
    MedicationController medicationController;
    @Mock
    MedicationService medicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveMedication() {
        // Success
        when(medicationService.saveMedication(any(MedicationDto.class))).thenReturn(getMockMedicationDto());
        ResponseEntity<MessageResponseDto> responseDto = medicationController.saveMedication(getMockMedicationDto());
        assertNotNull(responseDto.getBody());
        assertEquals(ResponseMessages.MEDICATION_CREATED, Objects.requireNonNull(responseDto.getBody()).getMessage());

        // Failed
        when(medicationService.saveMedication(any(MedicationDto.class))).thenReturn(null);
        ResponseEntity<MessageResponseDto> responseNullDto = medicationController.saveMedication(getMockMedicationDto());
        assertEquals(HttpStatus.BAD_REQUEST, responseNullDto.getStatusCode());
    }

    @Test
    void getMedications() {
        when(medicationService.getMedications()).thenReturn(List.of(getMockMedicationDto()));
        ResponseEntity<List<MedicationDto>> medicationDtoList = medicationController.getMedications();
        assertNotNull(medicationDtoList.getBody());
        assertEquals("MED", Objects.requireNonNull(medicationDtoList.getBody()).get(0).getCode());
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