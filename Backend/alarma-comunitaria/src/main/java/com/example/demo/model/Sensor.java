package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tiposensorid", nullable = false)
    private TipoSensor tipoSensor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Casaid", nullable = false)
    private Casa casa;

    public Sensor(TipoSensor tipoSensor, Casa casa) {
        this.tipoSensor = tipoSensor;
        this.casa = casa;
    }

    @Override
    public String toString() {
        return "Sensor [id=" + id + ", tipoSensor=" + tipoSensor + ", casa=" + casa + "]";
    }

    // constructor, getters, and setters

    
}

