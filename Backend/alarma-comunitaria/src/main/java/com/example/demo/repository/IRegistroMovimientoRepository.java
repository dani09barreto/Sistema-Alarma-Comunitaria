package com.example.demo.repository;

import com.example.demo.model.RegistroMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistroMovimientoRepository extends JpaRepository<RegistroMovimiento, Long> {
}
