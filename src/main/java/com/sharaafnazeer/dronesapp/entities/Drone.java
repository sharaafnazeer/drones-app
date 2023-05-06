package com.sharaafnazeer.dronesapp.entities;

import com.sharaafnazeer.dronesapp.enums.DroneModel;
import com.sharaafnazeer.dronesapp.enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "drone")
public class Drone {

    @Id
    @Column(name = "serialNumber", length = 100)
    private String serialNumber;

    @Column(name = "model")
    @Enumerated(value = EnumType.STRING)
    private DroneModel model;

    @Column(name = "maxWeight")
    @Positive
    @Max(value = 500, message = "Drone cannot carry more than {value} grams")
    private Double maxWeight;

    @Column(name = "batteryLife")
    @Positive
    @Max(value = 100, message = "Battery life cannot be more than 100%")
    private Integer batteryLife;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private DroneState state;

    @Column(name = "currentWeight")
    private Double currentWeight; // This will keep track the weight of the drone during loading, to prevent

}
