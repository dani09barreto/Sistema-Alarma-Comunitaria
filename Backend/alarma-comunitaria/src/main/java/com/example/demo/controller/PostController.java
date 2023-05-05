package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.patterns.builder.CasaBuilder;
import com.example.demo.patterns.builder.RegistroMovimientoBuilder;
import com.example.demo.patterns.builder.SensorBuilder;
import com.example.demo.payload.request.CasaRequest;
import com.example.demo.payload.response.CasaResponse;
import com.example.demo.payload.request.RegistryRequest;
import com.example.demo.payload.request.SensorRequest;
import com.example.demo.security.payload.MessageResponse;
import com.example.demo.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/api/post")
public class PostController {

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


    @PostMapping("/add/house")
    public ResponseEntity <CasaResponse> createHouse(@RequestBody CasaRequest casaRequest) {
        Barrio barrio = barrioService.getBarrioById(casaRequest.getBarrioId());
        Cliente cliente = clientService.findByCedula(casaRequest.getIdentificacionCliente());
        Casa casa = new CasaBuilder()
                .setBarrio(barrio)
                .setCliente(cliente)
                .setDireccion(casaRequest.getDireccion())
                .setOcupada(false)
                .build();
        casaService.saveCasa(casa);
        return ResponseEntity.ok(new CasaResponse(casa.getId()));
    }

    @PostMapping("/add/sensors")
    public ResponseEntity <?> createSensor(@RequestBody SensorRequest sensorRequest){
        sensorRequest.getIdTipoSensor().forEach(id -> {
            Casa casa = casaService.getCasaById(sensorRequest.getIdCasa());
            TipoSensor tipoSensor = tipoSensorService.getTipoSensorById(id);
            Sensor sensor = new SensorBuilder()
                    .setCasa(casa)
                    .setTipoSensor(tipoSensor)
                    .build();
            sensorService.saveSensor(sensor);
        });
        return ResponseEntity.ok(new MessageResponse("Sensor creado con exito"));
    }

    @PostMapping("/add/registry")
    public ResponseEntity <?> createRegistry(@RequestBody RegistryRequest registryRequest){
        Sensor sensor = sensorService.getSensorById(registryRequest.getSensorId());
        RegistroMovimiento registroMovimiento = new RegistroMovimientoBuilder()
                .setSensor(sensor)
                .setFecha(LocalDate.now())
                .build();
        registroMovimientoService.saveRegistroMovimiento(registroMovimiento);
        return ResponseEntity.ok(new MessageResponse("Registro creado con exito"));
    }

}
