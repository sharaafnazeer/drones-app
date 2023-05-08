/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadDroneDto {

    @JsonProperty(value = "droneSerial", required = true)
    @NotNull(message = ResponseMessages.DRONE_SERIAL_EMPTY)
    @NotEmpty(message = ResponseMessages.DRONE_SERIAL_EMPTY)
    @NotBlank(message = ResponseMessages.DRONE_SERIAL_EMPTY)
    private String droneSerial;

    @JsonProperty(value = "medicationCodes", required = true)
    @NotNull(message = ResponseMessages.MEDICATION_CODE_EMPTY)
    @NotEmpty(message = ResponseMessages.MEDICATION_CODE_EMPTY)
    private List<String> medicationCodes;
}
