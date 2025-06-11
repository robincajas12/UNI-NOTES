package com;

public class Main {
    public static void main(String[] args) {
        FacultadCiencias science = new FacultadCiencias();
        Iterator it = science.crearIndice();
        ICarreras engineering = new EngineeringFaculty();
        Iterator it2 = engineering.crearIndice();
        System.out.println("xd");
        while(it2.hasNext())
        {
            System.out.println(it2.next());
        }
    }
}
