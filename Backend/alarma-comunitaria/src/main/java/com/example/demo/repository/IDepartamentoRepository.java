package com.example.demo.repository;

import com.example.demo.model.Departamento;
import com.example.demo.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepartamentoRepository extends JpaRepository<Departamento, Long> {
    List <Departamento> findAllByPais(Pais pais);
}
