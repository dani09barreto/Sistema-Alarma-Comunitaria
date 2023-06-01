package com.example.demo.service.intf;

import com.example.demo.model.RegistroMovimiento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRegistroMovimientoService {
    RegistroMovimiento saveRegistroMovimiento(RegistroMovimiento registroMovimiento);

    RegistroMovimiento getRegistroMovimientoBySensorId(Long id);
    List<RegistroMovimiento> findAll();
}
