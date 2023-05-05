package com.example.demo.payload.response;

import com.example.demo.model.TipoEmergencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TiposEmergencias {
    private List<TipoEmergencia> emergencias = new ArrayList<>();
}
