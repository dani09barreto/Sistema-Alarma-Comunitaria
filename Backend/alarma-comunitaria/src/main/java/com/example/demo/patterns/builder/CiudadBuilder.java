package com.example.demo.patterns.builder;

import com.example.demo.model.Ciudad;
import com.example.demo.model.Departamento;

public class CiudadBuilder implements IBuilder <Ciudad> {
    private String nombre;

    private Departamento departamento;

    public CiudadBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public CiudadBuilder setDepartamento(Departamento departamento) {
        this.departamento = departamento;
        return this;
    }

    @Override
    public Ciudad build() {
        return new Ciudad(nombre, departamento);
    }
}
