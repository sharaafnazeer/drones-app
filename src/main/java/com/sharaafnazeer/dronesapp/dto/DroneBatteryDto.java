/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneBatteryDto {
    private String serialNumber;
    private int batteryLife;

}
