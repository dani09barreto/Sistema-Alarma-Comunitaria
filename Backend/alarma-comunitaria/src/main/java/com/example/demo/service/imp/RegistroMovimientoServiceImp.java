package com.example.demo.service.imp;

import com.example.demo.model.RegistroMovimiento;
import com.example.demo.patterns.observer.DatabaseObserver;
import com.example.demo.repository.IRegistroMovimientoRepository;
import com.example.demo.service.intf.IRegistroMovimientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RegistroMovimientoServiceImp implements IRegistroMovimientoService {

    private List<DatabaseObserver> observers = new ArrayList<>();

    @Autowired
    private IRegistroMovimientoRepository registroMovimientoRepository;

    @Override
    public RegistroMovimiento saveRegistroMovimiento(RegistroMovimiento registroMovimiento) {
        RegistroMovimiento savedRegistroMovimiento = registroMovimientoRepository.save(registroMovimiento);

        // Después de la inserción exitosa, notificar a los observadores
        for (DatabaseObserver observer : observers) {
            observer.notifyNewInsertion(registroMovimiento);
        }

        return savedRegistroMovimiento;
    }

    @Override
    public RegistroMovimiento getRegistroMovimientoBySensorId(Long id) {
        return registroMovimientoRepository.findById(id).orElse(null);
    }

    @Override
    public void registerObserver(DatabaseObserver observer) {
        observers.add(observer);
    }


}
