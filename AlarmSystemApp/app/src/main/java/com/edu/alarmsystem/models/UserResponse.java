package com.edu.alarmsystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserResponse {
    private Long id;
    private Long identificacion;
    private String correo;

    public UserResponse() {
    }

    public UserResponse(Long id, Long identificacion, String correo) {
        this.id = id;
        this.identificacion = identificacion;
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Long identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", identificacion=" + identificacion +
                ", correo='" + correo + '\'' +
                '}';
    }
}