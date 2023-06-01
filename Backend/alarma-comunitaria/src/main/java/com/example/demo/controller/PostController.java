package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.patterns.builder.CasaBuilder;
import com.example.demo.patterns.builder.RegistroMovimientoBuilder;
import com.example.demo.patterns.builder.RespuestaEmergenciaBuilder;
import com.example.demo.patterns.builder.SensorBuilder;
import com.example.demo.patterns.observer.DatabaseObserverComponent;
import com.example.demo.payload.request.CasaRequest;
import com.example.demo.payload.request.CasaTipoEmergencias;
import com.example.demo.payload.response.CasaResponse;
import com.example.demo.payload.request.RegistryRequest;
import com.example.demo.payload.request.SensorRequest;
import com.example.demo.security.payload.MessageResponse;
import com.example.demo.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Qualifier("tipoEmergenciaServiceImp")
    @Autowired
    private ITipoEmergenciaService tipoEmergenciaService;

    @Qualifier("repuestaEmergenciaServiceImp")
    @Autowired
    private IRespuestaEmergenciaService respuestaEmergenciaService;

    @Autowired
    private DatabaseObserverComponent databaseObserverComponent;


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

    @PostMapping("/add/typeEmergencies")
    public ResponseEntity <?> addEmergenciesToHouse(@RequestBody CasaTipoEmergencias casaTipoEmergencias){
        Casa casa = casaService.getCasaById(casaTipoEmergencias.getIdCasa());
        List<TipoEmergencia> emergencias = new ArrayList<>();
        casaTipoEmergencias.getTiposEmergenciasId().stream()
                .map(aLong -> tipoEmergenciaService.obtenerTipoEmergenciaById(aLong))
                .forEach(emergencias::add);
 
        for (TipoEmergencia tipoEmergencia : emergencias) {
            RespuestaEmergencia resp = new RespuestaEmergenciaBuilder()
                           .setCasa(casa)
                           .setTipoEmergencia(tipoEmergencia)
                           .build();
            respuestaEmergenciaService.guardarRespuestaEmergencias(resp);
        }
    
        return ResponseEntity.ok("Emergencias añadidas correctamente");
    }

    // Registro de Movimiento
    @PostMapping("/add/registry/movement/sensor/id={id}")
    public ResponseEntity <?> createRegistryMovement(@PathVariable Long id){


        Sensor sensor = sensorService.getSensorById(id);
        Casa casa = sensor.getCasa();
        RegistroMovimiento registroMovimiento = new RegistroMovimientoBuilder()
                .setSensor(sensor)
                .setFecha(LocalDate.now())
                .build();

        registroMovimientoService.saveRegistroMovimiento(registroMovimiento);
        return ResponseEntity.ok(new MessageResponse("Registro creado con exito para el sensor con id: " + sensor.getId()
                + " en la fecha: " + LocalDate.now() + " a las: " + ZonedDateTime.now(ZoneId.of("America/Bogota")).toLocalDateTime()
                + " de la casa con id: " + casa.getId() + " en la direccion: " + casa.getDireccion()
                + " del cliente: " + casa.getCliente().getNombre() + " " + casa.getCliente().getApellido()
                + " con correo: " + casa.getCliente().getCorreoElectronico()
                + " y numero de telefono: " + casa.getCliente().getCelular()
        ));
    }

    @PostMapping("/id={id}/ocupacion")
    public ResponseEntity<String> updateCasa(@PathVariable Long id, @RequestBody CasaRequest casaRequest) {
        
        Casa casa = casaService.getCasaById(id);
        Barrio barrio = barrioService.getBarrioById(casaRequest.getBarrioId());
        Cliente cliente = clientService.findById(casaRequest.getIdentificacionCliente());
        casa.setOcupada(casaRequest.getOcupada());
        casa.setBarrio(barrio);
        casa.setCliente(cliente);
        casa.setDireccion(casaRequest.getDireccion());
        casaService.saveCasa(casa);
        return ResponseEntity.ok("Ocupación actualizada correctamente");
    }

}
