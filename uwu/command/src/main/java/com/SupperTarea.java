package com;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SupperTarea implements TareaProducto{

    List<TareaProducto> tareas = new ArrayList<>();
    @Override
    public void ejecutar(Producto producto) {
        tareas.forEach(t -> t.ejecutar(producto));
    }

    public void addTarea(TareaProducto tareaProducto){
        tareas.add(tareaProducto);
    }
    public void removeTarea(TareaProducto tareaProducto){
        tareas.remove(tareaProducto);
    }
}
