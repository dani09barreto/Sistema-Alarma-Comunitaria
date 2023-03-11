package com.example.demo.repository;

import com.example.demo.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDepartamentoRepository extends JpaRepository<Departamento, Long> {
}
