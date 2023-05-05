package com.example.demo.service.imp;

import com.example.demo.model.TipoEmergencia;
import com.example.demo.repository.ITipoEmergenciaRepository;
import com.example.demo.service.intf.ITipoEmergenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoEmergenciaServiceImp implements ITipoEmergenciaService {

    @Autowired
    private ITipoEmergenciaRepository tipoEmergenciaRepository;

    @Override
    public List<TipoEmergencia> obtenerTiposEmergencia() {
        return tipoEmergenciaRepository.findAll();
    }
}
