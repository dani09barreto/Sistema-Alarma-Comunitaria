package com.example.demo.service.intf;

import com.example.demo.model.TipoEmergencia;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITipoEmergenciaService {
    List <TipoEmergencia> obtenerTiposEmergencia();
}
