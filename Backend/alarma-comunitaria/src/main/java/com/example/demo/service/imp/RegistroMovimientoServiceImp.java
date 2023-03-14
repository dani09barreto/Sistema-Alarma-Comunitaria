package com.example.demo.service.imp;

import com.example.demo.model.RegistroMovimiento;
import com.example.demo.repository.IRegistroMovimientoRepository;
import com.example.demo.service.intf.IRegistroMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class RegistroMovimientoServiceImp implements IRegistroMovimientoService {

    @Autowired
    private IRegistroMovimientoRepository registroMovimientoRepository;

    @Override
    public RegistroMovimiento saveRegistroMovimiento(RegistroMovimiento registroMovimiento) {
        return registroMovimientoRepository.save(registroMovimiento);
    }

    @Override
    public RegistroMovimiento getRegistroMovimientoBySensorId(Long id) {
        return registroMovimientoRepository.findById(id).orElse(null);
    }


}
