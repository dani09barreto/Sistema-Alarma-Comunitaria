package com.example.demo.service.intf;

import com.example.demo.model.Pais;
import org.springframework.stereotype.Service;

@Service
public interface IPaisService {
    Pais getPaisById(Long id);
}
