package com.example.demo.service.imp;

import com.example.demo.model.Cliente;
import com.example.demo.repository.IClientRepository;
import com.example.demo.service.intf.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImp implements IClientService {

    @Autowired
    private IClientRepository clientRepository;

    @Override
    public List<Cliente> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clientRepository.save(cliente);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Boolean existsByCedula(Long cedula) {
        return clientRepository.existsByIdentificacion(cedula);
    }

    @Override
    public Boolean existsByCorreoElectronico(String correo) {
        return clientRepository.existsByCorreoElectronico(correo);
    }

    @Override
    public Boolean existsByCelular(Long celular) {
        return clientRepository.existsByCelular(celular);
    }

    @Override
    public Cliente findByCedula(Long cedula) {
        return clientRepository.findByIdentificacion(cedula);
    }

}
