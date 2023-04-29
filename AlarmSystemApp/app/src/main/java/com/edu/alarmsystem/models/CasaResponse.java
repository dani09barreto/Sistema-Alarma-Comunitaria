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
    private Long casaId;
    private String direccion;
    private Long clienteId;
    private Long barrioId;

    public CasaResponse(Long id) {

    }


    public String getDireccion() {
        return direccion;
    }
}
