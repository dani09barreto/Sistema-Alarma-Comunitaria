package com.edu.alarmsystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CasaResponse {
    private String direccion;
    private Long clienteId;
    private Long barrioId;
    private Boolean ocupada;

    public CasaResponse(Long id) {

    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getBarrioId() {
        return barrioId;
    }

    public void setBarrioId(Long barrioId) {
        this.barrioId = barrioId;
    }

    public Boolean getOcupada() {
        return ocupada;
    }

    public void setOcupada(Boolean ocupada) {
        this.ocupada = ocupada;
    }

    @Override
    public String toString() {
        return "CasaResponse{" +
                "direccion='" + direccion + '\'' +
                ", clienteId=" + clienteId +
                ", barrioId=" + barrioId +
                ", ocupada=" + ocupada +
                '}';
    }
}
