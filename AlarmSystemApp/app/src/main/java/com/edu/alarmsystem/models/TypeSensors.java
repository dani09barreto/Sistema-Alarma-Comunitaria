package com.edu.alarmsystem.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeSensors {
    private Long id;
    private String nombre;

    @Override
    public String toString() {
        return nombre;
    }
}
