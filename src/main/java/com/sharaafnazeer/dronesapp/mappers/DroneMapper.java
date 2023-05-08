package com.sharaafnazeer.dronesapp.mappers;

import com.sharaafnazeer.dronesapp.dto.DroneBatteryDto;
import com.sharaafnazeer.dronesapp.dto.DroneDto;
import com.sharaafnazeer.dronesapp.entities.Drone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DroneMapper {

    DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);

    @Mapping(target = "weightLoaded", source = "drone.currentWeight")
    @Mapping(target = "state", source = "drone.state")
    DroneDto droneToDroneDto(Drone drone);

    List<DroneDto> dronesToDronesDto(List<Drone> drones);

    Drone droneDtoToDrone(DroneDto dto);

    @Mapping(target="serialNumber",source="drone.serialNumber")
    @Mapping(target="batteryLife",source="drone.batteryLife")
    DroneBatteryDto droneToBatteryDto(Drone drone);
}
