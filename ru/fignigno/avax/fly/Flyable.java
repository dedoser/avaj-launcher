package ru.fignigno.avax.fly;

import ru.fignigno.avax.WeatherTower;

public interface Flyable {
    void updateConditions();
    void registerTower(WeatherTower weatherTower);
}
