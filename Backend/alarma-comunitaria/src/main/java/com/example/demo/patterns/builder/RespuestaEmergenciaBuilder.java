package com.example.demo.patterns.builder;

import com.example.demo.model.Casa;
import com.example.demo.model.RespuestaEmergencia;
import com.example.demo.model.TipoEmergencia;

public class RespuestaEmergenciaBuilder implements IBuilder <RespuestaEmergencia>{

    private Casa casa;
    private TipoEmergencia tipoEmergencia;

    public RespuestaEmergenciaBuilder setCasa(Casa casa) {
        this.casa = casa;
        return this;
    }

    public RespuestaEmergenciaBuilder setTipoEmergencia(TipoEmergencia tipoEmergencia) {
        this.tipoEmergencia = tipoEmergencia;
        return this;
    }

    @Override
    public RespuestaEmergencia build() {
        return new RespuestaEmergencia(casa, tipoEmergencia);
    }
}
