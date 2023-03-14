package com.example.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CasaRequest {
    private Long identificacionCliente;
    private Long barrioId;
    private String nombre;

    private String barrioNombre;
    private String direccion;
}
