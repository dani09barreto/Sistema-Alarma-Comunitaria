package com.example.demo.service.intf;

import com.example.demo.model.Ciudad;
import com.example.demo.model.Departamento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICiudadService {
    Ciudad getCiudadById(Long id);
    List <Ciudad> getCiudadesByDepartamento(Departamento departamento);
}
