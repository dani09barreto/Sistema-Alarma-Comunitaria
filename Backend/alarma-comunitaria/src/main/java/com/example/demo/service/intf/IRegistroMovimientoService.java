package com.example.demo.service.intf;

import com.example.demo.model.RegistroMovimiento;
import com.example.demo.patterns.observer.DatabaseObserver;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface IRegistroMovimientoService {
    RegistroMovimiento saveRegistroMovimiento(RegistroMovimiento registroMovimiento);

    RegistroMovimiento getRegistroMovimientoBySensorId(Long id);
    
    void registerObserver(DatabaseObserver observer);
}
