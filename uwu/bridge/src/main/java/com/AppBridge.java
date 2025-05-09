package com;

import com.models.Azul;
import com.models.Naranja;
import com.models.Negro;

import com.impl.FactoryImpl;
import com.interfaces.IColor;
import com.models.Circulo;
import com.models.Figura;
import com.models.Rectangulo;
import com.models.Triangulo;

import java.lang.reflect.InvocationTargetException;

public class AppBridge {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        IColor colorNegro = new Negro();
        IColor colorNaranja = new Naranja();
        IColor colorAzul = new Azul();


        FactoryImpl factory = new FactoryImpl();
        factory.load("com");



        // Crear figuras con diferentes colores
        Figura trianguloNegro = (Figura) factory.get("triangulo", "negro");
        Figura cuadradoNaranja = (Figura) factory.get("cuadrado", "naranja"); // Rectángulo en vez de Cuadrado
        Figura circuloAzul = (Figura) factory.get("circulo", "azul");

        // Dibujar las figuras y modificar sus bordes
        trianguloNegro.dibujarFigura(5);
        trianguloNegro.modificarBorde(6);

        cuadradoNaranja.dibujarFigura(4);
        cuadradoNaranja.modificarBorde(3);

        circuloAzul.dibujarFigura(7);
        circuloAzul.modificarBorde(2);
        }
}
