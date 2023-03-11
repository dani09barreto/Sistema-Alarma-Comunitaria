package com.example.demo.repository;

import com.example.demo.model.Casa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICasaRepository extends JpaRepository<Casa, Long> {
}
