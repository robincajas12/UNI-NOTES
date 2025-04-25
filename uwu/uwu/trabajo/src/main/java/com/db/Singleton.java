package com.db;

import com.anotations.MiComponente;
@MiComponente(name = "ProductServices")
public class Singleton {
    private static Singleton instance = null;
    private static DbConfig dbCon = null;

    private Singleton() {
        try
        {
            var con = Singleton.dbCon.getConnection();
            System.out.println("Conectado siuuu");
        }catch(Exception e)
        {
            System.out.println("hola mundo");
        }
    }

    public static Singleton getInstante()
    {
        Singleton.dbCon = new DbConfig();
        if(instance == null) instance = new Singleton();
        else System.out.println("Se devolvió instancia");
        return instance;
    }
}

