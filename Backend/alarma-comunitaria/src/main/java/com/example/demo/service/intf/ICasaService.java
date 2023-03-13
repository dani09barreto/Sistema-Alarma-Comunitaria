package com.example.demo.service.intf;

import com.example.demo.model.Casa;
import org.springframework.stereotype.Service;

@Service
public interface ICasaService {
    Casa saveCasa(Casa casa);
    Casa getCasaById(Long id);

    Object getAllCasas();
}
