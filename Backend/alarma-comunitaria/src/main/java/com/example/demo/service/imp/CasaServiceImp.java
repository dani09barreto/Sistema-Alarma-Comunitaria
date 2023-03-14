package com.example.demo.service.imp;

import com.example.demo.model.Casa;
import com.example.demo.repository.ICasaRepository;
import com.example.demo.service.intf.ICasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CasaServiceImp implements ICasaService {
    @Autowired
    private ICasaRepository casaRepository;

    @Override
    public Casa saveCasa(Casa casa) {
        return casaRepository.save(casa);
    }

    @Override
    public Casa getCasaById(Long id) {
        return casaRepository.findById(id).get();
    }
}
