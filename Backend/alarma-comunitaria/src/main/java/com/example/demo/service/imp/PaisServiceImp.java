package com.example.demo.service.imp;

import com.example.demo.repository.IPaisRepository;
import com.example.demo.service.intf.IPaisService;
import org.springframework.stereotype.Service;

@Service
public class PaisServiceImp implements IPaisService {
    private IPaisRepository paisRepository;
}
