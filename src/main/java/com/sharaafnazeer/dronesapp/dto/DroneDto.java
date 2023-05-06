package com.sharaafnazeer.dronesapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharaafnazeer.dronesapp.enums.DroneModel;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneDto {

    @NotNull(message = "Please provide a serial number")
    @Size(min = 3, max = 100, message = "Drone serial number must not be greater than {value} characters")
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String serialNumber;

    @JsonProperty(required = true)
    @NotNull(message = "Please provide a model")
    private DroneModel model;

    @DecimalMax(value = "500", message = "Drone cannot carry more than {value} grams")
    private Double maxWeight;

    @JsonProperty(required = true)
    @NotNull(message = "Please provide a battery life")
    private Integer batteryLife;

    private String state;
    private Double weightLoaded;
}
