package com.example.demo.service.imp;

import com.example.demo.model.Departamento;
import com.example.demo.repository.IDepartamentoRepository;
import com.example.demo.service.intf.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoServiceImp implements IDepartamentoService {
    @Autowired
    private IDepartamentoRepository departamentoRepository;

    @Override
    public Departamento getDepartamentoById(Long id) {
        return departamentoRepository.findById(id).get();
    }
}
