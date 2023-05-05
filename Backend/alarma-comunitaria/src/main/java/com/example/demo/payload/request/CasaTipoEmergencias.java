package com.example.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CasaTipoEmergencias {
    private Long idCasa;
    private Set <Long> tiposEmergenciasId;
}
