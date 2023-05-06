package com.sharaafnazeer.dronesapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sharaafnazeer.dronesapp.constants.AppConstants;
import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationDto {

    @JsonProperty(required = true)
    @NotEmpty(message = ResponseMessages.MEDICATION_NAME_EMPTY)
    @NotBlank(message = ResponseMessages.MEDICATION_NAME_EMPTY)
    @Pattern(regexp = "[a-zA-Z_0-9-]+", message = ResponseMessages.MEDICATION_NAME_ERROR)
    private String name;

    @JsonProperty(required = true)
    @NotNull(message = ResponseMessages.MEDICATION_WEIGHT_EMPTY)
    @Max(value = 500, message = ResponseMessages.MEDICATION_WEIGHT_ERROR)
    private Double weight;

    @JsonProperty(required = true)
    @NotEmpty(message = ResponseMessages.MEDICATION_CODE_EMPTY)
    @NotBlank(message = ResponseMessages.MEDICATION_CODE_EMPTY)
    @Pattern(regexp = "[A-Z0-9_]+", message = ResponseMessages.MEDICATION_CODE_ERROR)
    private String code;

    private String image;

    @JsonIgnore
    private DroneDto drone;
}
