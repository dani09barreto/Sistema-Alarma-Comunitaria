package com.example.demo.service.intf;

import com.example.demo.model.TipoSensor;
import org.springframework.stereotype.Service;

@Service
public interface ITipoSensorService {
    TipoSensor getTipoSensorById(Long id);

    Object getAllTipoSensores();
}
