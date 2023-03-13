package com.example.demo.service.intf;

import com.example.demo.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IClientService {
    List <Cliente> findAll();
    Optional<Cliente> findById(Long id);
    Cliente save(Cliente cliente);
    void deleteById(Long id);
    Boolean existsByCedula(Long cedula);
    Boolean existsByCorreoElectronico(String correo);
    Boolean existsByCelular(Long celular);
    Cliente findByCedula(Long cedula);

    List<Cliente> getAllClients();

}
