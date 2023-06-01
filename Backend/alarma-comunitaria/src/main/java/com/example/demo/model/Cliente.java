package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Usuarioid")
    private User usuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "identificacion")
    private Long identificacion;

    @Column(name = "celular")
    private Long celular;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    public Cliente(User usuario, String nombre, String apellido, Long identificacion, Long celular, String correoElectronico) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.celular = celular;
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", usuario=" + usuario + ", nombre=" + nombre + ", apellido=" + apellido
                + ", identificacion=" + identificacion + ", celular=" + celular + ", correoElectronico="
                + correoElectronico + "]";
    }

    // getters y setters

    
}