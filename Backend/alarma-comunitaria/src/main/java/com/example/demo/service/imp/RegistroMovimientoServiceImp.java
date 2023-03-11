package com.example.demo.service.imp;

import com.example.demo.repository.IRegistroMovimientoRepository;
import com.example.demo.service.intf.IRegistroMovimientoService;
import org.springframework.stereotype.Service;

@Service
public class RegistroMovimientoServiceImp implements IRegistroMovimientoService {
    private IRegistroMovimientoRepository registroMovimientoRepository;
}
