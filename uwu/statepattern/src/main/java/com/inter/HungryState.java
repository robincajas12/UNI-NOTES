package com.inter;

import com.model.Tamagochi;

public final class HungryState implements State {
    @Override
    public void jugar(Tamagochi tamagochi) {
        stateChange.onNext("vamos jugar!!");

    }

    @Override
    public void alimentar(Tamagochi tamagochi) {
        stateChange.onNext("quiero jugar, nmo quiero comer");
        tamagochi.setState(new HappyState());
    }

    @Override
    public void dormir(Tamagochi tamagochi) {
        stateChange.onNext("No quiero dormir");
    }
}
