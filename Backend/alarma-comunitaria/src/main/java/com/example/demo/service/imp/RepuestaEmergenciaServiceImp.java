package com.example.demo.service.imp;

import com.example.demo.repository.IRespuestaEmergenciaRepository;
import com.example.demo.service.intf.IRespuestaEmergenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepuestaEmergenciaServiceImp implements IRespuestaEmergenciaService {

    @Autowired
    private IRespuestaEmergenciaRepository respuestaEmergenciaRepository;
}
