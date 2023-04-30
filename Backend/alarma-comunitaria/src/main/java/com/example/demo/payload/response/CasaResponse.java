package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CasaResponse {
    private Long casaId;
    private String direccion;
    private Long clienteId;
    private Long barrioId;

    public CasaResponse() {
    }

    public CasaResponse(Long id, String direccion, Long clienteId, Long barrioId) {
        this.casaId = id;
        this.direccion = direccion;
        this.clienteId = clienteId;
        this.barrioId = barrioId;
    }


    public CasaResponse(Long id) {
    }

    // Getters and Setters

    public Long getCasaId() {
        return this.casaId;
    }

    public void setCasaId(Long casaId) {
        this.casaId = casaId;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion= direccion;
    }

    public Long getClienteId() {
        return this.clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getBarrioId() {
        return this.barrioId;
    }

    public void setBarrioId(Long barrioId) {
        this.barrioId = barrioId;
    }


    

    
}
