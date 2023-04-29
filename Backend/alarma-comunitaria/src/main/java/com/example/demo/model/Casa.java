package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "casa")
public class Casa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Clienteid")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Barrioid")
    private Barrio barrio;

    @Column(name = "direccion")
    private String direccion;

    public Casa(Cliente cliente, Barrio barrio, String direccion) {
        this.cliente = cliente;
        this.barrio = barrio;
        this.direccion = direccion;
    }

    public Long getIdentificacionCliente() {
        return cliente.getId();
    }
    // getters y setters
    public Long getId() {
        return this.id;
    }
}
