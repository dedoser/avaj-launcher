package ru.fignigno.avax;

import ru.fignigno.avax.fly.Flyable;

import java.util.ArrayList;
import java.util.List;

public abstract class Tower {
    private List<Flyable> observers = new ArrayList<>();
    private List<Flyable> unregisteredAircrafts = new ArrayList<>();

    public void register(Flyable flyable) {
        observers.add(flyable);
    }

    public void unregister(Flyable flyable) {
        unregisteredAircrafts.add(flyable);
    }

    protected void conditionsChanged() {
        if (!unregisteredAircrafts.isEmpty()) {
            observers.removeAll(unregisteredAircrafts);
            unregisteredAircrafts = new ArrayList<>();
        }
        observers.forEach(Flyable::updateConditions);
    }
}
