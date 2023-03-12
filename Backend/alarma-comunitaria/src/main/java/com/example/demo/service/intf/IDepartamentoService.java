package com.example.demo.service.intf;

import com.example.demo.model.Departamento;
import org.springframework.stereotype.Service;

@Service
public interface IDepartamentoService {
    Departamento getDepartamentoById(Long id);
}
