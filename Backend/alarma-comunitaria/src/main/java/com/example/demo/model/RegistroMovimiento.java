package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "registromovimiento")
public class RegistroMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Sensorid", nullable = false)
    private Sensor sensor;

    private LocalDate fecha;

    public RegistroMovimiento(Sensor sensor, LocalDate fecha) {
        this.sensor = sensor;
        this.fecha = fecha;
    }

    // constructor, getters, and setters
}

