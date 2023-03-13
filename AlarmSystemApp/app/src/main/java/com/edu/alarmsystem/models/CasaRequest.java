package com.edu.alarmsystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CasaRequest {
    private Long identificacionCliente;
    private Long barrioId;
    private String direccion;
}