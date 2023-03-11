package com.example.demo.patterns.builder;

import com.example.demo.model.Barrio;
import com.example.demo.model.Cliente;

public interface IBuilder <T> {
    public T build();
}
