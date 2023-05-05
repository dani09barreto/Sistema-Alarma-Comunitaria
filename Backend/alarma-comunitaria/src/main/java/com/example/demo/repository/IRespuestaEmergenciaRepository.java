package com.example.demo.repository;

import com.example.demo.model.Casa;
import com.example.demo.model.RespuestaEmergencia;
import com.example.demo.model.TipoEmergencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRespuestaEmergenciaRepository extends JpaRepository<RespuestaEmergencia, Long> {
    @Query("select r from RespuestaEmergencia r join fetch r.casa join fetch r.tipoEmergencia where r.casa.id = :casaId")
    List<RespuestaEmergencia> findByCasaId(Long casaId);
}
