package com.example.demo.controller;

import ch.qos.logback.core.net.server.Client;
import com.example.demo.model.*;
import com.example.demo.payload.request.CasaRequest;
import com.example.demo.payload.response.*;
import com.example.demo.service.intf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Qualifier("paisServiceImp")
    @Autowired
    private IPaisService paisService;

    @Qualifier("departamentoServiceImp")
    @Autowired
    private IDepartamentoService departamentoService;

    @Qualifier("ciudadServiceImp")
    @Autowired
    private ICiudadService ciudadService;


    // Get Methods

    // Get house by id
    @GetMapping ("/house/id={id}")
    public ResponseEntity<?> getHouseById(@PathVariable Long id) {
        CasaRequest casaRequest = new CasaRequest();
        Casa casa = casaService.getCasaById(id);
        // Get client by id
        casaRequest.setIdentificacionCliente(casa.getIdentificacionCliente());
        Optional<Cliente> client = clientService.findById(casa.getIdentificacionCliente());
        casaRequest.setNombre(client.get().getNombre());

        // Get barrio by id
        Barrio barrio = barrioService.getBarrioById(casa.getBarrio().getId());
        casaRequest.setBarrioId(casa.getBarrio().getId());
        casaRequest.setBarrioNombre(barrio.getNombre());
        casaRequest.setDireccion(casa.getDireccion());
        return ResponseEntity.ok(casaRequest);

    }


    @GetMapping ("/all/sensortypes")
    public ResponseEntity<?> getAllSensorTypes() {
        return ResponseEntity.ok(tipoSensorService.getAllTipoSensores());
    }

    // Sensor by house id
    @GetMapping ("/house/id={id}/sensors")
    public ResponseEntity<?> getSensorsByHouseId(@PathVariable Long id) {
        Casa casa = casaService.getCasaById(id);
        // Get all sensors
        List<Sensor> sensores = sensorService.getAllSensors();
        //  Cast to sensorResponse
        List<SensorResponse> sensorResponses = new ArrayList<>();

        sensores.stream()
                .filter(sensor -> sensor.getCasa().getId().equals(casa.getId()))
                .map(sensor -> new SensorResponse(sensor.getId(),sensor.getCasa().getId(),
                                                    sensor.getTipoSensor().getId(),
                                                    sensor.getTipoSensor().getNombre()))
                .forEach(sensorResponses::add);

        return ResponseEntity.ok(sensorResponses);

    }

    // Get house by id and return all movements
    @GetMapping ("/house/id={id}/sensors/movements")
    public ResponseEntity<?> getMovementsByHouseId(@PathVariable Long id) {
        Casa casa = casaService.getCasaById(id);
        // Get all sensors
        List<Sensor> sensores = sensorService.getAllSensors();
        //  Cast to sensorResponse
        List<SensorResponse> sensorResponses = new ArrayList<>();

        sensores.stream()
                .filter(sensor -> sensor.getCasa().getId().equals(casa.getId()))
                .map(sensor -> new SensorResponse(sensor.getId(),sensor.getCasa().getId(),
                        sensor.getTipoSensor().getId(),
                        sensor.getTipoSensor().getNombre()))
                .forEach(sensorResponses::add);

        // Using sensorResponse get all movements
        List<Long> sensorIdRegistroMovimientos = new ArrayList<>();
        //Extract sensor id
        sensorResponses.stream()
                .map(sensorResponse -> sensorResponse.getId())
                .forEach(sensorIdRegistroMovimientos::add);
        // Print sensor id
        sensorIdRegistroMovimientos.stream()
                .forEach(System.out::println);

        // Get all movements by sensor id
        List<RegistroMovimiento> registroMovimientos = new ArrayList<>();
        sensorIdRegistroMovimientos.stream()
                .map(sensorId -> registroMovimientoService.getRegistroMovimientoBySensorId(sensorId))
                .forEach(registroMovimientos::add);

        // Print movements
        registroMovimientos.stream()
                .forEach(System.out::println);

        //Clear null values
        registroMovimientos.removeIf(registroMovimiento -> registroMovimiento == null);


        //Cast to MovimientoResponse
        List<MovimientoResponse> movimientoResponses = new ArrayList<>();
        registroMovimientos.stream()
                .map(registroMovimiento -> new MovimientoResponse(registroMovimiento.getId(),
                        registroMovimiento.getFecha(),
                        registroMovimiento.getSensor().getId()))
                .forEach(movimientoResponses::add);

        return ResponseEntity.ok(movimientoResponses);



    }

    @GetMapping("/all/countries")
    public ResponseEntity<?> getAllCountries() {
        List<Pais> paises = paisService.getAllPaises();
        return ResponseEntity.ok(paises);
    }

    @GetMapping("/country={idPais}/departments")
    public ResponseEntity<?> getDepartmentsByCountryId(@PathVariable Long idPais) {
        Pais pais = paisService.getPaisById(idPais);
        List<Departamento> departamentos = departamentoService.getDepartamentosByPais(pais);
        List <DepartamentoResponse> departamentoResponses = new ArrayList<>();

        departamentos.stream()
                        .map(departamento -> new DepartamentoResponse(departamento.getId(), departamento.getNombre()))
                        .forEach(departamentoResponses::add);

        return ResponseEntity.ok(departamentoResponses);
    }

    @GetMapping("/department={idDepartamento}/cities")
    public ResponseEntity<?> getCitiesByDepartmentId(@PathVariable Long idDepartamento) {
        Departamento departamento = departamentoService.getDepartamentoById(idDepartamento);
        List<Ciudad> ciudades = ciudadService.getCiudadesByDepartamento(departamento);
        List <CiudadResponse> ciudadResponses = new ArrayList<>();

        ciudades.stream()
                .map(ciudad -> new CiudadResponse(ciudad.getId(), ciudad.getNombre()))
                .forEach(ciudadResponses::add);

        return ResponseEntity.ok(ciudadResponses);
    }

    @GetMapping("/city={idCiudad}/neighborhoods")
    public ResponseEntity<?> getNeighborhoodsByCityId(@PathVariable Long idCiudad) {
        Ciudad ciudad = ciudadService.getCiudadById(idCiudad);
        List<Barrio> barrios = barrioService.getBarriosByCiudad(ciudad);
        List <BarrioResponse> barrioResponses = new ArrayList<>();

        barrios.stream()
                .map(barrio -> new BarrioResponse(barrio.getId(), barrio.getNombre()))
                .forEach(barrioResponses::add);

        return ResponseEntity.ok(barrioResponses);
    }


}
