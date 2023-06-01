package com.example.demo.repository;

import com.example.demo.model.RegistroMovimiento;
import com.example.demo.patterns.observer.DatabaseObserver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRegistroMovimientoRepository extends JpaRepository<RegistroMovimiento, Long> {
    List<RegistroMovimiento> getRegistroMovimientoBySensorId(Long id);
    default void registerObserver(DatabaseObserver observer) {
      
    }

    default void saveAndNotify(RegistroMovimiento registroMovimiento, List<DatabaseObserver> observers){
        save(registroMovimiento);

        // Después de la inserción exitosa, notificar a los observadores
        for (DatabaseObserver observer : observers) {
            observer.notifyNewInsertion(registroMovimiento);
        }
    }
}
