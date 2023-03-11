package com.example.demo.repository;

import com.example.demo.model.TipoSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITipoSensorRepository extends JpaRepository<TipoSensor, Long> {
}
