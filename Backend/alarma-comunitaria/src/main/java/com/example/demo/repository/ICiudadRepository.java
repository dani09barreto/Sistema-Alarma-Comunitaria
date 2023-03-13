package com.example.demo.repository;

import com.example.demo.model.Ciudad;
import com.example.demo.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICiudadRepository extends JpaRepository<Ciudad, Long> {
    List<Ciudad> findAllByDepartamento(Departamento departamento);
}
