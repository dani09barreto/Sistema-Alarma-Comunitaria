package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "registromovimiento")
public class RegistroMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Sensorid", nullable = false)
    private Sensor sensor;

    @Column(name="fecha")
    private LocalDate fecha;

    @Column(name="hora")
    private LocalDateTime hora;


    // AllArgsConstructors and NoArgsConstructors

    public RegistroMovimiento( Sensor sensor, LocalDate fecha,LocalDateTime hora){
        this.sensor = sensor;
        this.fecha = fecha;
        this.hora = hora;
    }
    // Getters y Setters

}

