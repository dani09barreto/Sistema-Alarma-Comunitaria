package com.example.demo.service.imp;

import com.example.demo.repository.ITipoSensorRepository;
import com.example.demo.service.intf.ITipoSensorService;
import org.springframework.stereotype.Service;

@Service
public class TipoSensorServiceImp implements ITipoSensorService {
    private ITipoSensorRepository tipoSensorRepository;
}
