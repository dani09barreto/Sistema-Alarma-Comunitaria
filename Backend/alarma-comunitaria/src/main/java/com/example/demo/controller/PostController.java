package com.example.demo.controller;

import com.example.demo.model.Casa;
import com.example.demo.service.intf.ICasaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/post")
public class PostController {

    @Qualifier("casaServiceImp")
    @Autowired
    private ICasaService casaService;

    @PostMapping("/create/house")
    public ResponseEntity <Casa> createHouse() {

        return null;
    }
}
