package com.example.demo.repository;

import com.example.demo.model.Casa;
import com.example.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICasaRepository extends JpaRepository<Casa, Long> {
    Casa findByCliente(Cliente cliente);

}
