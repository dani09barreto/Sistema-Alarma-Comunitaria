package com.example.demo.repository;

import com.example.demo.model.Barrio;
import com.example.demo.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBarrioRepository extends JpaRepository<Barrio, Long> {
    List <Barrio> findAllByCiudad(Ciudad ciudad);
}
