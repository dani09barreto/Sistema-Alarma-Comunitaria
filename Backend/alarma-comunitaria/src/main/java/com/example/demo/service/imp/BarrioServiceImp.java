package com.example.demo.service.imp;

import com.example.demo.repository.IBarrioRepository;
import com.example.demo.service.intf.IBarrioService;
import org.springframework.stereotype.Service;

@Service
public class BarrioServiceImp implements IBarrioService {
    private IBarrioRepository barrioRepository;
}
