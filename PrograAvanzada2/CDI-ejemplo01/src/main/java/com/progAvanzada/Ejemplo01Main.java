package com.progAvanzada;

import com.progAvanzada.servicios.inter.ServicioBooks;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class Ejemplo01Main {
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()){
            ServicioBooks servicioBooks = container.select(ServicioBooks.class).get();
            System.out.println(servicioBooks.findById("978-0-123456-47-2").get());
            servicioBooks.findAll().forEach(System.out::println );
        }catch (Exception e){

        }
    }
}
