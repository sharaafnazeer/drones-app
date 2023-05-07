package com.sharaafnazeer.dronesapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import com.sharaafnazeer.dronesapp.enums.DroneModel;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneDto {

    @NotNull(message = ResponseMessages.DRONE_SERIAL_EMPTY)
    @Size(min = 3, max = 100, message = ResponseMessages.DRONE_SERIAL_ERROR)
    @JsonProperty(required = true)
    @NotEmpty(message = ResponseMessages.DRONE_SERIAL_EMPTY)
    @NotBlank(message = ResponseMessages.DRONE_SERIAL_EMPTY)
    private String serialNumber;

    @JsonProperty(required = true)
    @NotNull(message = ResponseMessages.DRONE_MODEL_EMPTY)
    private DroneModel model;

    @NotNull(message = ResponseMessages.DRONE_MAX_WEIGHT_EMPTY)
    @JsonProperty(required = true)
    @Max(value = 500, message = ResponseMessages.DRONE_MAX_WEIGHT_ERROR)
    private Double maxWeight;

    @JsonProperty(required = true)
    @NotNull(message = ResponseMessages.DRONE_BATTERY_EMPTY)
    @Positive(message = ResponseMessages.DRONE_BATTERY_ERROR)
    @Max(value = 100, message = ResponseMessages.DRONE_BATTERY_ERROR)
    private Integer batteryLife;

    private DroneState state;
    private Double weightLoaded;
}
