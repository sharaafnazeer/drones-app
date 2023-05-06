package com.sharaafnazeer.dronesapp.services;

import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.entities.Medication;
import com.sharaafnazeer.dronesapp.mappers.MedicationMapper;
import com.sharaafnazeer.dronesapp.repos.MedicationRepository;
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
}
