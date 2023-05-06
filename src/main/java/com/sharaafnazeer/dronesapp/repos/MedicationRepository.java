package com.sharaafnazeer.dronesapp.repos;

import com.sharaafnazeer.dronesapp.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, String> {

    List<Medication> findAll();

    Medication findByCode(String code);

    List<Medication> findAllByCodeIn(List<String> codes);
}
