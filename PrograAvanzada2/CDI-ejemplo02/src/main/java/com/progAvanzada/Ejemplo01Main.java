package com.progAvanzada;

import com.progAvanzada.servicios.Hola;
import com.progAvanzada.servicios.Operaciones;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Inject;

@ApplicationScoped
public class Ejemplo01Main {

  //  @Inject
   // Operaciones op;

    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize();) {
            Instance<Operaciones> operaciones = container.select(Operaciones.class,NamedLiteral.of("uwu"));
            Operaciones operaciones1= operaciones.get();
            Instance<Operaciones> operaciones2 = container.select(Operaciones.class,NamedLiteral.of("opConf"));
            Operaciones operacionesimpl= operaciones.get();
            System.out.println(operacionesimpl.sumar(1,2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
