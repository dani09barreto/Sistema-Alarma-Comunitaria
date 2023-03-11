package com.example.demo.repository;

import com.example.demo.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISensorRepository extends JpaRepository<Sensor, Long> {
}
