package com.inter;

import com.model.Tamagochi;

public  final class HappyState implements State {
    @Override
    public void jugar(Tamagochi tamagochi) {
        stateChange.onNext("Yesssss...... vamos a jugar!");
        tamagochi.setState(new SleepyState());
    }

    @Override
    public void alimentar(Tamagochi tamagochi) {
        stateChange.onNext("Noooo, no quiero comer");
    }

    @Override
    public void dormir(Tamagochi tamagochi) {
        stateChange.onNext("No quiero dormir");
    }
}
