package com.inter;

import com.model.Tamagochi;

public final class SleepyState implements State {

    @Override
    public void jugar(Tamagochi tamagochi) {
        stateChange.onNext("Estoy cansado");
    }

    @Override
    public void alimentar(Tamagochi tamagochi) {
        stateChange.onNext("Noooo, no quiero comer");
    }

    @Override
    public void dormir(Tamagochi tamagochi) {
        stateChange.onNext("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        tamagochi.setState(new HungryState());
    }
}
