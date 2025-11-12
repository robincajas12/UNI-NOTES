package com.progAvanzada.conf;

import com.progAvanzada.servicios.Operaciones;
import com.progAvanzada.servicios.OperacionesImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Qualifier;

@ApplicationScoped
public class OperacionesConf  {
    @Produces
    @ApplicationScoped
    @Named("opConf")
    public Operaciones operaciones(){
        return new OperacionesImpl();
    }
}
