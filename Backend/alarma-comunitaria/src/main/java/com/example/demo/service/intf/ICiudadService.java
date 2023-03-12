package com.example.demo.service.intf;

import com.example.demo.model.Ciudad;
import org.springframework.stereotype.Service;

@Service
public interface ICiudadService {
    Ciudad getCiudadById(Long id);
}
