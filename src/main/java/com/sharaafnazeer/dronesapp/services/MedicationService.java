package com.sharaafnazeer.dronesapp.services;

import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.entities.Medication;

import java.util.List;

public interface MedicationService {

    MedicationDto saveMedication(MedicationDto medicationDto);

    List<MedicationDto> getMedications();

    MedicationDto getMedicationByCode(String code);
}
