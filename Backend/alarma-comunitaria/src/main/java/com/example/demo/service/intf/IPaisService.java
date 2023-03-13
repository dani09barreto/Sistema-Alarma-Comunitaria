package com.example.demo.service.intf;

import com.example.demo.model.Pais;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPaisService {
    Pais getPaisById(Long id);
    List <Pais> getAllPaises();
}
