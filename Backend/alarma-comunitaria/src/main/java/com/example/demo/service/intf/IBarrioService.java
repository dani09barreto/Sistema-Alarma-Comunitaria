package com.example.demo.service.intf;

import com.example.demo.model.Barrio;
import com.example.demo.model.Ciudad;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBarrioService {
    Barrio getBarrioById(Long id);
    List <Barrio> getBarriosByCiudad(Ciudad ciudad);
}
