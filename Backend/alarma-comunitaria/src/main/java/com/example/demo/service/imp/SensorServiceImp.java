package com.example.demo.service.imp;

import com.example.demo.model.Casa;
import com.example.demo.model.Sensor;
import com.example.demo.repository.ISensorRepository;
import com.example.demo.service.intf.ISensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorServiceImp implements ISensorService {

    @Autowired
    private ISensorRepository sensorRepository;

    @Override
    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    @Override
    public Sensor getSensorById(Long id) {
        return sensorRepository.findById(id).get();
    }

    @Override
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

}
