package com.sharaafnazeer.dronesapp.services.impl;

import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.entities.Drone;
import com.sharaafnazeer.dronesapp.entities.Medication;
import com.sharaafnazeer.dronesapp.exceptions.DroneException;
import com.sharaafnazeer.dronesapp.mappers.MedicationMapper;
import com.sharaafnazeer.dronesapp.repos.MedicationRepository;
import com.sharaafnazeer.dronesapp.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicationMapper mapper;

    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper mapper) {
        this.medicationRepository = medicationRepository;
        this.mapper = mapper;
    }

    @Override
    public MedicationDto saveMedication(MedicationDto medicationDto) {

        // Find if any medications available with existing code
        MedicationDto existing = getMedicationByCode(medicationDto.getCode());
        if (existing != null) {
            throw new DroneException(ResponseMessages.MEDICATION_FOUND);
        }

        Medication medication = mapper.medicationDtoToMedication(medicationDto);
        Medication createdMedication = medicationRepository.save(medication);
        return mapper.medicationToMedicationDto(createdMedication);
    }

    @Override
    public List<MedicationDto> getMedications() {
        List<Medication> medications = medicationRepository.findAll();
        return mapper.medicationsToMedicationDtos(medications);
    }

    @Override
    public MedicationDto getMedicationByCode(String code) {
        Medication medication = medicationRepository.findByCode(code);
        return mapper.medicationToMedicationDto(medication);
    }

    @Override
    public List<MedicationDto> getMedicationsByDrone(Drone drone) {
        List<Medication> medications = medicationRepository.findByDrone(drone);
        return mapper.medicationsToMedicationDtos(medications);
    }
}
