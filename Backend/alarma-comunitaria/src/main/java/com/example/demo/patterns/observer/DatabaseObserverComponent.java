package com.example.demo.patterns.observer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.example.demo.model.Casa;
import com.example.demo.model.RegistroMovimiento;
import com.example.demo.service.imp.MailService;
import com.example.demo.service.intf.ICasaService;
import com.example.demo.service.intf.IRegistroMovimientoService;


@Component
public class DatabaseObserverComponent implements DatabaseObserver {

    @Autowired
    private ICasaService casaService;

    @Autowired
    private MailService mailService;

    public DatabaseObserverComponent(IRegistroMovimientoService registroMovimientoService) {
        registroMovimientoService.registerObserver(this);
    }

    @Override
    public void notifyNewInsertion(RegistroMovimiento registroMovimiento) {
        Casa casa = casaService.getCasaById(registroMovimiento.getSensor().getCasa().getId());
        mailService.sendEmail("soportequickparked@gmail.com",casa.getCliente().getCorreoElectronico(), "ALERTA: Registro de movimiento",
        "Se ha registrado un movimiento en su casa ubicada en la direccion: " + casa.getDireccion() + " a las: "
                + ZonedDateTime.now(ZoneId.of("America/Bogota")).toLocalDateTime() + " del dia: " + LocalDate.now());

    }
}
