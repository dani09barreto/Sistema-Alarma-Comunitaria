package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Clienteid")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Barrioid")
    private Barrio barrio;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ocupada")
    private Boolean ocupada;

    public Casa(Cliente cliente, Barrio barrio, String direccion, Boolean ocupada) {
        this.cliente = cliente;
        this.barrio = barrio;
        this.direccion = direccion;
        this.ocupada = ocupada;
    }

    public Long getIdentificacionCliente() {
        return cliente.getId();
    }
    // getters y setters
    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Casa [id=" + id + ", cliente=" + cliente + ", barrio=" + barrio + ", direccion=" + direccion
                + ", ocupada=" + ocupada + "]";
    }

    
}
