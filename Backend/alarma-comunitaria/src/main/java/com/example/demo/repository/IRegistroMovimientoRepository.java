package com.example.demo.repository;

import com.example.demo.model.RegistroMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegistroMovimientoRepository extends JpaRepository<RegistroMovimiento, Long> {
    List<RegistroMovimiento> getRegistroMovimientoBySensorId(Long id);
    List<RegistroMovimiento> findAll();
}
