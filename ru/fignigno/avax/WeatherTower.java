package ru.fignigno.avax;

import ru.fignigno.avax.Tower;
import ru.fignigno.avax.fly.Coordinates;
import ru.fignigno.avax.provider.WeatherProvider;

public class WeatherTower extends Tower {

    public String getWeather(Coordinates coordinates) {
        return WeatherProvider.getWeatherProvider().getCurrentWeather(coordinates);
    }

    void changeWeather() {
        this.conditionsChanged();
    }
}
