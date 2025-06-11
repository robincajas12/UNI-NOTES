package com;

import java.util.LinkedList;

public class FacultadCiencias implements ICarreras{
    public LinkedList<String> carreras;
    public FacultadCiencias(){
        carreras = new LinkedList<>();
        carreras.add("Math");
        carreras.add("Physics");
        carreras.add("Data Science");
    }
    @Override
    public Iterator crearIndice() {
        return new ScienceIterator(carreras);
    }
    public class ScienceIterator implements Iterator {
        private LinkedList<String> items;
        private int index;
        public ScienceIterator(LinkedList<String> carreras){
            this.items = carreras;
            this.index = 0;
        }
        @Override
        public void primerElemento() {
            index = 0;

        }

        @Override
        public String next() {
            return carreras.get(index++);
        }

        @Override
        public Boolean hasNext() {
            return index < carreras.size();
        }

        @Override
        public String element() {
            return carreras.get(index);
        }
    }
}
