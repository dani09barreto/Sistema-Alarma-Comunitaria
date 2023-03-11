package com.example.demo.patterns.builder;

import com.example.demo.model.Casa;
import com.example.demo.model.Sensor;
import com.example.demo.model.TipoSensor;

public class SensorBuilder implements IBuilder <Sensor>{

    private TipoSensor tipoSensor;
    private Casa casa;

    public SensorBuilder setTipoSensor(TipoSensor tipoSensor) {
        this.tipoSensor = tipoSensor;
        return this;
    }

    public SensorBuilder setCasa(Casa casa) {
        this.casa = casa;
        return this;
    }

    @Override
    public Sensor build() {
        return new Sensor(tipoSensor, casa);
    }
}
