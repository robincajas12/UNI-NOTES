package com;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TareaProducto correo = new TareaEnviarCorreo();
        TareaProducto validar = new ValidarProducto();
        TareaProducto imprimirProducto = new TareaImprimirProducto();
        SupperTarea superT = new SupperTarea();
        superT.addTarea(correo);
        superT.addTarea(validar);
        superT.addTarea(imprimirProducto);
        GestorDeTareas miGestor = new GestorDeTareas();
        miGestor.ejecutar(superT, new Producto("juan",400f));
    }
}