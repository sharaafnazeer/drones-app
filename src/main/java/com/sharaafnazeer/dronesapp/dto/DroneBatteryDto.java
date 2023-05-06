package com.sharaafnazeer.dronesapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneBatteryDto {
    private String serialNumber;
    private String model;
    private int batteryLife;

}
