package com.example.demo.service.imp;

import com.example.demo.model.Barrio;
import com.example.demo.model.Ciudad;
import com.example.demo.repository.IBarrioRepository;
import com.example.demo.service.intf.IBarrioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarrioServiceImp implements IBarrioService {

    @Autowired
    private IBarrioRepository barrioRepository;

    @Override
    public Barrio getBarrioById(Long id) {
        return barrioRepository.findById(id).get();
    }

    @Override
    public List<Barrio> getBarriosByCiudad(Ciudad ciudad) {
        return barrioRepository.findAllByCiudad(ciudad);
    }

}
