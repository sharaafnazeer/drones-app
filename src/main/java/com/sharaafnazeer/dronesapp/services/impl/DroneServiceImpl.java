package com.sharaafnazeer.dronesapp.services.impl;

import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import com.sharaafnazeer.dronesapp.dto.DroneBatteryDto;
import com.sharaafnazeer.dronesapp.dto.DroneDto;
import com.sharaafnazeer.dronesapp.dto.LoadDroneDto;
import com.sharaafnazeer.dronesapp.dto.MedicationDto;
import com.sharaafnazeer.dronesapp.entities.Drone;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import com.sharaafnazeer.dronesapp.exceptions.DroneException;
import com.sharaafnazeer.dronesapp.mappers.DroneMapper;
import com.sharaafnazeer.dronesapp.repos.DroneRepository;
import com.sharaafnazeer.dronesapp.services.DroneService;
import com.sharaafnazeer.dronesapp.services.MedicationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sharaafnazeer.dronesapp.constants.AppConstants.BATTERY_LIMIT;

@Service
@Transactional
public class DroneServiceImpl implements DroneService {
    private final DroneRepository droneRepository;
    private final DroneMapper mapper;
    private final MedicationService medicationService;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper mapper, MedicationService medicationService) {
        this.droneRepository = droneRepository;
        this.mapper = mapper;
        this.medicationService = medicationService;
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
    public List<DroneDto> getDrones() {
        List<Drone> drones = droneRepository.findAll();
        return mapper.dronesToDronesDto(drones);
    }

    @Override
    public List<DroneDto> getAvailableDrones() {
        List<DroneState> status = new ArrayList<>();
        status.add(DroneState.IDLE);
        status.add(DroneState.LOADING);
        List<Drone> drones = droneRepository.findByStateIn(status);
        return mapper.dronesToDronesDto(drones);
    }

    @Override
    public DroneBatteryDto checkDroneBattery(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);

        return mapper.droneToBatteryDto(drone);
    }

    @Override
    public void loadDrone(LoadDroneDto loadDroneDto) {
        Drone existingDrone = droneRepository.findBySerialNumber(loadDroneDto.getDroneSerial());
        if (existingDrone == null) {
            throw new DroneException(ResponseMessages.DRONE_NOT_FOUND);
        }

        if (existingDrone.getBatteryLife() < BATTERY_LIMIT) {
            throw new DroneException(ResponseMessages.DRONE_BATTERY_LOW);
        }

        if (!(existingDrone.getState().equals(DroneState.IDLE) || existingDrone.getState().equals(DroneState.LOADING))) {
            throw new DroneException(ResponseMessages.DRONE_LOADING_STATE_EXCEEDED);
        }

        loadDroneDto.getMedicationCodes().forEach(code -> {
            MedicationDto medication = medicationService.getMedicationByCode(code);
            if (medication == null) {
                throw new DroneException(ResponseMessages.MEDICATION_NOT_FOUND);
            }

            Double newWeight = existingDrone.getCurrentWeight() + medication.getWeight();
            if (newWeight > existingDrone.getMaxWeight()) {
                throw new DroneException(ResponseMessages.DRONE_MAX_WEIGHT_EXCEEDED);
            }
            if (newWeight < existingDrone.getMaxWeight()) {
                existingDrone.setCurrentWeight(newWeight);
                existingDrone.setState(DroneState.LOADING);
            }
            if (newWeight.equals(existingDrone.getMaxWeight())) {
                existingDrone.setCurrentWeight(newWeight);
                existingDrone.setState(DroneState.LOADED);
            }
            medication.setDrone(mapper.droneToDroneDto(existingDrone));
            medicationService.saveMedication(medication);
        });
        droneRepository.save(existingDrone);
    }

    @Override
    public List<MedicationDto> getMedicationsByDrone(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber);
        if (drone == null) {
            throw new DroneException(ResponseMessages.DRONE_NOT_FOUND);
        }
        return medicationService.getMedicationsByDrone(drone);
    }
}
