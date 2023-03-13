package com.example.demo.service.imp;

import com.example.demo.model.Departamento;
import com.example.demo.model.Pais;
import com.example.demo.repository.IDepartamentoRepository;
import com.example.demo.service.intf.IDepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoServiceImp implements IDepartamentoService {
    @Autowired
    private IDepartamentoRepository departamentoRepository;

    @Override
    public Departamento getDepartamentoById(Long id) {
        if (departamentoRepository.findById(id).isPresent()) {
            return departamentoRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<Departamento> getDepartamentosByPais(Pais pais) {
        return departamentoRepository.findAllByPais(pais);
    }
}
