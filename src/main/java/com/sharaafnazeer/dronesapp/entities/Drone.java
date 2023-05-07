package com.sharaafnazeer.dronesapp.entities;

import com.sharaafnazeer.dronesapp.enums.DroneModel;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "drone")
public class Drone {

    @Id
    @Column(name = "serialNumber", length = 100)
    private String serialNumber;

    @Column(name = "model", length = 15)
    @Enumerated(value = EnumType.STRING)
    private DroneModel model;

    @Column(name = "maxWeight")
    @Positive
    private Double maxWeight;

    @Column(name = "batteryLife")
    @Positive
    private Integer batteryLife;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private DroneState state;

    @Column(name = "currentWeight")
    private Double currentWeight;

    @OneToMany(mappedBy = "drone")
    private Set<Medication> medications;

}
