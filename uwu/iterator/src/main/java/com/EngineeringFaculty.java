package com;

public class EngineeringFaculty implements ICarreras{
    String[] careers;

    public EngineeringFaculty()
    {
        careers = new String[5];
        careers[0] = "Civil Engineering";
        careers[1] = "Computer Science";
        careers[2] = "System information engineering";
        careers[3] = "Industrial Design Engineering";
        careers[4] = "Mechanical Engineering";
    }
    @Override
    public Iterator crearIndice() {
        return new EngineeringIterator(careers);
    }

    public class EngineeringIterator implements Iterator {
        private  String[] careers;
        private  int index;

        public EngineeringIterator(String[] careers) {
            this.careers= careers;
            this.index = 0;
        }
        @Override
        public void primerElemento() {
            index =0;
        }

        @Override
        public String next() {
            return careers[index++];
        }

        @Override
        public Boolean hasNext() {
            return index < careers.length;
        }

        @Override
        public String element() {
            return careers[index];
        }
    }
}
