package com.example.demo.controller;

import com.example.demo.model.Barrio;
import com.example.demo.model.Ciudad;
import com.example.demo.model.Departamento;
import com.example.demo.model.Pais;
import com.example.demo.payload.response.BarrioResponse;
import com.example.demo.payload.response.CiudadResponse;
import com.example.demo.payload.response.DepartamentoResponse;
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
    @GetMapping ("/all/houses")
    public ResponseEntity<?> getAllHouses() {
        return ResponseEntity.ok(casaService.getAllCasas());
    }

    // Get house by id
    @GetMapping ("/house/{id}")
    public ResponseEntity<?> getHouseById(@PathVariable Long id) {
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
