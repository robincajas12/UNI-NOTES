package com.inter;

import com.model.Tamagochi;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public sealed interface State permits HappyState, HungryState, SleepyState {
    BehaviorSubject<String> stateChange = BehaviorSubject.create();
    void jugar(Tamagochi tamagochi);
    void alimentar(Tamagochi tamagochi);
    void dormir(Tamagochi tamagochi);
}
