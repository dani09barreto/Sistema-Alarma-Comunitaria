package com.example.demo.service.imp;

import com.example.demo.repository.ISensorRepository;
import com.example.demo.service.intf.ISensorService;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImp implements ISensorService {
    private ISensorRepository sensorRepository;
}
