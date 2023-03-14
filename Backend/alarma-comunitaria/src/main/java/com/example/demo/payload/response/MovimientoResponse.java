package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Setter
public class MovimientoResponse {

    private Long id;
    private Long sensorId;
    private LocalDate fecha;


    public MovimientoResponse(Long id, LocalDate fecha, Long id1) {
        this.id = id;
        this.fecha = fecha;
        this.sensorId = id1;
    }
}
