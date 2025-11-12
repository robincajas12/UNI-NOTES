package com.progAvanzada.servicios;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("uwu")
public class OperacionesImpl implements Operaciones {
    @Override
    public int sumar(int a, int b) {
        if (b >= 0) {
            for (int i = 0; i < b; i++) {
                a += 1;
            }
        } else {
            for (int i = 0; i > b; i--) {
                a -= 1;
            }
        }
        return a;
    }
}