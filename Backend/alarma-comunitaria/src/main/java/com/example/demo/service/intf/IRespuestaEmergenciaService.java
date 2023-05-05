package com.example.demo.service.intf;

import com.example.demo.model.Casa;
import com.example.demo.model.RespuestaEmergencia;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRespuestaEmergenciaService {
    List <RespuestaEmergencia> obtenerRespuestasEmergenciaCasa(Long id);
}
