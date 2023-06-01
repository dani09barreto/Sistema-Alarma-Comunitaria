package com.example.demo.patterns.observer;

import com.example.demo.model.RegistroMovimiento;


public interface DatabaseObserver {
    void notifyNewInsertion(RegistroMovimiento registroMovimiento);
}
