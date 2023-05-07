package com.sharaafnazeer.dronesapp.repos;

import com.sharaafnazeer.dronesapp.entities.Drone;
import com.sharaafnazeer.dronesapp.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {

    List<Medication> findAll();

    Medication findByCode(String code);

    List<Medication> findByDrone(Drone drone);
}
