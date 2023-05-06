package com.sharaafnazeer.dronesapp.repos;

import com.sharaafnazeer.dronesapp.entities.Drone;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    List<Drone> findAll();
    List<Drone> findByStateIn(List<DroneState> states);
    Drone findBySerialNumber(String serialNumber);

}
