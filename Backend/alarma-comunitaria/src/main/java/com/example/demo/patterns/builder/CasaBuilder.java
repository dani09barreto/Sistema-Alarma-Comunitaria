package com.example.demo.patterns.builder;

import com.example.demo.model.Barrio;
import com.example.demo.model.Casa;
import com.example.demo.model.Cliente;

public class CasaBuilder implements IBuilder<Casa>{

    private Cliente cliente;

    private Barrio barrio;

    private String direccion;

    private Boolean ocupada;

    public CasaBuilder setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public CasaBuilder setBarrio(Barrio barrio) {
        this.barrio = barrio;
        return this;
    }

    public CasaBuilder setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public CasaBuilder setOcupada(Boolean ocupada) {
        this.ocupada = ocupada;
        return this;
    }

    @Override
    public Casa build() {
        return new Casa(cliente, barrio, direccion, ocupada);
    }
}
