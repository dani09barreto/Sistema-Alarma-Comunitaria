package com.example.demo.patterns.builder;

import com.example.demo.model.Pais;

public class PaisBuilder implements IBuilder <Pais> {

    private String nombre;

    public PaisBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public Pais build() {
        return new Pais(nombre);
    }
}
