package com.sharaafnazeer.dronesapp.services;

import com.sharaafnazeer.dronesapp.dto.DroneBatteryDto;
import com.sharaafnazeer.dronesapp.dto.DroneDto;
import com.sharaafnazeer.dronesapp.dto.LoadDroneDto;
import com.sharaafnazeer.dronesapp.dto.MedicationDto;

import java.util.List;

public interface DroneService {

    DroneDto registerDrone(DroneDto droneDto);

    DroneDto getDrone(String serialNumber);

    List<DroneDto> findAvailableDrones();

    DroneBatteryDto checkDroneBattery(String serialNumber);

    void loadDrone(LoadDroneDto loadDroneDto);

    List<MedicationDto> getMedicationsByDrone(String serialNumber);
}
