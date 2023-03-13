package com.example.demo.service.imp;

import com.example.demo.model.RegistroMovimiento;
import com.example.demo.repository.IRegistroMovimientoRepository;
import com.example.demo.service.intf.IRegistroMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistroMovimientoServiceImp implements IRegistroMovimientoService {

    @Autowired
    private IRegistroMovimientoRepository registroMovimientoRepository;

    @Override
    public RegistroMovimiento saveRegistroMovimiento(RegistroMovimiento registroMovimiento) {
        return registroMovimientoRepository.save(registroMovimiento);
    }

    @Override
    public Optional<RegistroMovimiento> getRegistroMovimientoBySensorId(Long id) {
        return registroMovimientoRepository.findById(id);
    }
}
