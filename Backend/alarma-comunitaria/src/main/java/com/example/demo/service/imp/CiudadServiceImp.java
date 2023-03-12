package com.example.demo.service.imp;

import com.example.demo.model.Ciudad;
import com.example.demo.repository.ICiudadRepository;
import com.example.demo.service.intf.ICiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadServiceImp implements ICiudadService {
    @Autowired
    private ICiudadRepository ciudadRepository;

    @Override
    public Ciudad getCiudadById(Long id) {
        return ciudadRepository.findById(id).get();
    }
}
