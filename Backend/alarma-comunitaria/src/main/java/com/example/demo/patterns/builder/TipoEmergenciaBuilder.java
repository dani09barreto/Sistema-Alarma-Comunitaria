package com.example.demo.patterns.builder;

import com.example.demo.model.TipoEmergencia;

public class TipoEmergenciaBuilder implements IBuilder <TipoEmergencia>{

    private String nombre;

    public TipoEmergenciaBuilder setNombre(String name) {
        this.nombre = name;
        return this;
    }

    @Override
    public TipoEmergencia build() {
        return new TipoEmergencia(nombre);
    }
}
