package com.sharaafnazeer.dronesapp.services;

import com.sharaafnazeer.dronesapp.dto.DroneBatteryDto;
import com.sharaafnazeer.dronesapp.dto.DroneDto;
import com.sharaafnazeer.dronesapp.entities.Drone;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import com.sharaafnazeer.dronesapp.mappers.DroneMapper;
import com.sharaafnazeer.dronesapp.repos.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final DroneMapper mapper;
    private static int BATTERY_LIMIT = 25;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper mapper) {
        this.droneRepository = droneRepository;
        this.mapper = mapper;
    }

    // Register and save drone
    @Override
    public DroneDto registerDrone(DroneDto droneDto) {
        Drone drone = mapper.droneDtoToDrone(droneDto);
        drone.setState(DroneState.IDLE);
        drone.setCurrentWeight(0.0);
        Drone createdDrone = droneRepository.save(drone);
        return mapper.droneToDroneDto(createdDrone);
    }

    @Override
    public DroneDto getDrone(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        return mapper.droneToDroneDto(drone);
    }

    @Override
    public List<DroneDto> findAvailableDrones() {
        List<Drone> drones = droneRepository.findByState(DroneState.IDLE);
        return mapper.dronesToDronesDto(drones);
    }

    @Override
    public DroneBatteryDto checkDroneBattery(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);

        return mapper.droneToBatteryDto(drone);
    }
}
