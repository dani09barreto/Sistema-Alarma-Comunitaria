package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime hora;
    

    public RegistroMovimiento(Sensor sensor, LocalDate fecha, LocalDateTime hora) {
        this.sensor = sensor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // constructor, getters, and setters
}

