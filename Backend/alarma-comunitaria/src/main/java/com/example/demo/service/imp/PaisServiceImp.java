package com.example.demo.service.imp;

import com.example.demo.model.Pais;
import com.example.demo.repository.IPaisRepository;
import com.example.demo.service.intf.IPaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisServiceImp implements IPaisService {
    @Autowired
    private IPaisRepository paisRepository;

    @Override
    public Pais getPaisById(Long id) {
        return paisRepository.findById(id).get();
    }
}
