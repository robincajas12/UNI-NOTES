package com;

import com.interfaces.IEmpleado;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static String [] empleadoTipo = {
            "Dev", "Tester"
    };
    private static String[] skills = {
            "Java", "C#", "python", "JavaScript"
    };
    private static Random rand = new Random();
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        EmpleadoFactory fac = new EmpleadoFactory();
        for (int i = 0; i < 10; i++)
        {
            IEmpleado empleado = fac.getIntance(getRandomEmpleado());
            empleado.habilidadAsignada(getRandHabilidad());
            empleado.tarea();
        }

    }

    public static String getRandHabilidad()
    {
        int op = rand.nextInt(skills.length);
        return skills[op];
    }
    public static String getRandomEmpleado()
    {
        int op = rand.nextInt(empleadoTipo.length);
        return empleadoTipo[op];
    }
}