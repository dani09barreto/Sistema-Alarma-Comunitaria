package com.example.demo.service.imp;

import com.example.demo.repository.IDepartamentoRepository;
import com.example.demo.service.intf.IDepartamentoService;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoServiceImp implements IDepartamentoService {
    private IDepartamentoRepository departamentoRepository;
}
