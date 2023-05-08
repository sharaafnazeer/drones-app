/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.services;

import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.entities.Drone;

import java.util.List;

public interface MedicationService {

    MedicationDto saveMedication(MedicationDto medicationDto, boolean isUpdate);

    List<MedicationDto> getMedications();

    MedicationDto getMedicationByCode(String code);

    List<MedicationDto> getMedicationsByDrone(Drone drone);
}
