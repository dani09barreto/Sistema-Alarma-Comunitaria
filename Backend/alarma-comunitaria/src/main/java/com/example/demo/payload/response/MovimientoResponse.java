package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
public class MovimientoResponse {

    private Long id;
    private Long sensorId;
    private LocalDate fecha;
    private LocalDateTime hora;


    public MovimientoResponse(Long id, LocalDate fecha, Long id1, LocalDateTime hora) {
        this.id = id;
        this.fecha = fecha;
        this.sensorId = id1;
        this.hora = hora;
    }
}
