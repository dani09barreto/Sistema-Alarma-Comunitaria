package com.example.demo.patterns.builder;

import com.example.demo.model.RegistroMovimiento;
import com.example.demo.model.Sensor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegistroMovimientoBuilder implements IBuilder <RegistroMovimiento>{

    private Sensor sensor;

    private LocalDate fecha;


    public RegistroMovimientoBuilder setSensor(Sensor sensor) {
        this.sensor = sensor;
        return this;
    }

    public RegistroMovimientoBuilder setFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }



    @Override
    public RegistroMovimiento build() {
        return new RegistroMovimiento(sensor, fecha );
    }
}
