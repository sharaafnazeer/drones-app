/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.mappers;

import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.entities.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicationMapper {

    DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);

    @Mapping(target = "drone", source = "medicationDto.drone")
    @Mapping(target = "image", source = "medicationDto.image")
    Medication medicationDtoToMedication(MedicationDto medicationDto);

    MedicationDto medicationToMedicationDto(Medication medication);

    List<Medication> medicationDtosToMedications(List<MedicationDto> medicationDto);
    List<MedicationDto> medicationsToMedicationDtos(List<Medication> medications);
}
