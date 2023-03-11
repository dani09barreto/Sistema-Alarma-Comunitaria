package com.example.demo.patterns.builder;

import com.example.demo.model.Barrio;
import com.example.demo.model.Ciudad;

public class BarrioBuilder implements IBuilder <Barrio> {

    private String nombre;

    private Ciudad ciudad;

    public BarrioBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public BarrioBuilder setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    @Override
    public Barrio build() {
        return new Barrio(nombre, ciudad);
    }

}
