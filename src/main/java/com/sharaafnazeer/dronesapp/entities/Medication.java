package com.sharaafnazeer.dronesapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    @NotNull()
    private String name;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "image")
    private String image;
}
