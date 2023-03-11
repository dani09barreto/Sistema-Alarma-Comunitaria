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
@Table(name = "respuestaemergencia")
public class RespuestaEmergencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Casaid", nullable = false)
    private Casa casa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tipoemergenciaid", nullable = false)
    private TipoEmergencia tipoEmergencia;

    public RespuestaEmergencia(Casa casa, TipoEmergencia tipoEmergencia) {
        this.casa = casa;
        this.tipoEmergencia = tipoEmergencia;
    }

    // constructor, getters, and setters
}