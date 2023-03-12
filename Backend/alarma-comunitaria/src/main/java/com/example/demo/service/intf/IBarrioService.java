package com.example.demo.service.intf;

import com.example.demo.model.Barrio;
import org.springframework.stereotype.Service;

@Service
public interface IBarrioService {
    Barrio getBarrioById(Long id);
}
