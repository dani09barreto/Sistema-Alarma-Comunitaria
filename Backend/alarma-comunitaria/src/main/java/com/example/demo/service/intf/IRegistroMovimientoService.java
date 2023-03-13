package com.example.demo.service.intf;

import com.example.demo.model.RegistroMovimiento;
import org.springframework.stereotype.Service;

@Service
public interface IRegistroMovimientoService {
    RegistroMovimiento saveRegistroMovimiento(RegistroMovimiento registroMovimiento);

    Object getRegistroMovimientoBySensorId(Long id);
}
