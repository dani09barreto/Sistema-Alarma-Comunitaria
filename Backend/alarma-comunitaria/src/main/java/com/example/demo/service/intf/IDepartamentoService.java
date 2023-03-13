package com.example.demo.service.intf;

import com.example.demo.model.Departamento;
import com.example.demo.model.Pais;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDepartamentoService {
    Departamento getDepartamentoById(Long id);
    List<Departamento> getDepartamentosByPais(Pais pais);
}
