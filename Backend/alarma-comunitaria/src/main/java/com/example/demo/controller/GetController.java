package com.example.demo.controller;

import com.example.demo.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/get")
public class GetController {

    // Service Get Method
    @Qualifier("casaServiceImp")
    @Autowired
    private ICasaService casaService;

    @Qualifier("barrioServiceImp")
    @Autowired
    private IBarrioService barrioService;

    @Qualifier("clientServiceImp")
    @Autowired
    private IClientService clientService;

    @Qualifier("tipoSensorServiceImp")
    @Autowired
    private ITipoSensorService tipoSensorService;

    @Qualifier("sensorServiceImp")
    @Autowired
    private ISensorService sensorService;

    @Qualifier("registroMovimientoServiceImp")
    @Autowired
    private IRegistroMovimientoService registroMovimientoService;


    // Get Methods
    @GetMapping ("/all/houses")
    public ResponseEntity<?> getAllHouses() {
        return ResponseEntity.ok(casaService.getAllCasas());
    }

    // Get house by id
    @GetMapping ("/house/{id}")
    public ResponseEntity<?> getHouseById(Long id) {
        return ResponseEntity.ok(casaService.getCasaById(id));
    }

    // Get tipos de sensores
    @GetMapping ("/all/sensortypes")
    public ResponseEntity<?> getAllSensorTypes() {
        return ResponseEntity.ok(tipoSensorService.getAllTipoSensores());
    }

    // Get client by id
    @GetMapping ("/client/{id}")
    public ResponseEntity<?> getClientById(Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }








}
