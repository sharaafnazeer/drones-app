package com.sharaafnazeer.dronesapp.entities;

import com.sharaafnazeer.dronesapp.constants.ResponseMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @Pattern(regexp = "[A-Z0-9_]+", message = ResponseMessages.MEDICATION_CODE_ERROR)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    @Pattern(regexp = "[a-zA-Z_0-9-]+", message = ResponseMessages.MEDICATION_NAME_ERROR)
    private String name;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_serial")
    private Drone drone; // A medication have one drone at a time.
}
