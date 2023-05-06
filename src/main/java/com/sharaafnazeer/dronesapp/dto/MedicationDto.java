package com.sharaafnazeer.dronesapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationDto {

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9.\\-\\/_ ]*$", message = "Only letters, numbers, _ and - are allowed")
    private String name;

    @JsonProperty(required = true)
    @Max(value = 500, message = "Weight cannot be greater than 500 grams")
    private Double weight;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9.\\-\\/_ ]*$", message = "Only letters, numbers, _ and - are allowed")
    private String code;

    private String image;
}
