package com.example.demo.service.imp;

import com.example.demo.model.TipoSensor;
import com.example.demo.repository.ITipoSensorRepository;
import com.example.demo.service.intf.ITipoSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoSensorServiceImp implements ITipoSensorService {

    @Autowired
    private ITipoSensorRepository tipoSensorRepository;

    @Override
    public TipoSensor getTipoSensorById(Long id) {
        return tipoSensorRepository.findById(id).get();
    }

    @Override
    public Object getAllTipoSensores() {
        return tipoSensorRepository.findAll();
    }
}
