package com.example.demo.payload.response;

import com.example.demo.model.TipoSensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SensorResponse {

    private Long id;
    private Long casaId;

    private Long idTipoSensor;
    private String tipoSensor;




}
