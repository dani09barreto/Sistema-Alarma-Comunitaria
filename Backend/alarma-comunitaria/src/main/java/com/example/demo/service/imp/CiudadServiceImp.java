package com.example.demo.service.imp;

import com.example.demo.repository.ICiudadRepository;
import com.example.demo.service.intf.ICiudadService;
import org.springframework.stereotype.Service;

@Service
public class CiudadServiceImp implements ICiudadService {
    private ICiudadRepository ciudadRepository;
}
