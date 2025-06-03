package com.model;

import com.inter.HungryState;
import com.inter.State;

public class Tamagochi {
    private State context;
    public Tamagochi()
    {
        this.context = new HungryState();
    }


    public void setState(State state) {

        this.context = state;
    }

    public State getStage() {
        return context;
    }
    public void jugar()
    {
        context.jugar(this);
    }
    public void alimentar()
    {
        context.alimentar(this);
    }
    public void zzzzzz()
    {
        context.dormir(this);
    }
}
