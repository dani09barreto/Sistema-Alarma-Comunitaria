package com.example.demo.patterns.builder;

import com.example.demo.model.TipoSensor;

public class TipoSensorBuilder implements IBuilder <TipoSensor>{

    private String nombre;

    public TipoSensorBuilder setnombre (String nombre){
        this.nombre = nombre;
        return this;
    }

    @Override
    public TipoSensor build() {
        return new TipoSensor(nombre);
    }
}
