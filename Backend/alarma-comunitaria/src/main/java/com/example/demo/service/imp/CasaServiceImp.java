package com.example.demo.service.imp;

import com.example.demo.repository.ICasaRepository;
import com.example.demo.service.intf.ICasaService;
import org.springframework.stereotype.Service;

@Service
public class CasaServiceImp implements ICasaService {
    private ICasaRepository casaRepository;
}
