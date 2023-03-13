package com.example.demo.service.intf;

import com.example.demo.model.Casa;
import com.example.demo.model.Sensor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISensorService {
    Sensor saveSensor(Sensor sensor);
    Sensor getSensorById(Long id);

    List<Sensor> getAllSensors();
}
