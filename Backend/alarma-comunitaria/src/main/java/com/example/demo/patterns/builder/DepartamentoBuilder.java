package com.example.demo.patterns.builder;

import com.example.demo.model.Ciudad;
import com.example.demo.model.Departamento;
import com.example.demo.model.Pais;

import java.util.HashSet;
import java.util.Set;

public class DepartamentoBuilder implements IBuilder <Departamento> {

    private String nombre;
    private Pais pais;


    public DepartamentoBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public DepartamentoBuilder setPais(Pais pais) {
        this.pais = pais;
        return this;
    }

    @Override
    public Departamento build() {
        return new Departamento(nombre, pais);
    }
}
