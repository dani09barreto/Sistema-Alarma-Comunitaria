package com.example.demo.service.imp;

import com.example.demo.model.Casa;
import com.example.demo.model.RespuestaEmergencia;
import com.example.demo.repository.IRespuestaEmergenciaRepository;
import com.example.demo.service.intf.IRespuestaEmergenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepuestaEmergenciaServiceImp implements IRespuestaEmergenciaService {

    @Autowired
    private IRespuestaEmergenciaRepository respuestaEmergenciaRepository;

    @Override
    public List<RespuestaEmergencia> obtenerRespuestasEmergenciaCasa(Long id) {
        return respuestaEmergenciaRepository.findByCasaId(id);
    }
}
