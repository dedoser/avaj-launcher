package ru.fignigno.avax.provider;

import ru.fignigno.avax.WeatherType;
import ru.fignigno.avax.fly.Coordinates;

import java.util.List;

public class WeatherProvider {

    private static final List<String> WEATHER = List.of(
            WeatherType.SUNNY,
            WeatherType.RAINY,
            WeatherType.FOGGY,
            WeatherType.SNOWY
    );

    private static WeatherProvider weatherProvider;

    private WeatherProvider() {
    }

    public static WeatherProvider getWeatherProvider() {
        if (weatherProvider == null) {
            weatherProvider = new WeatherProvider();
        }
        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        int num = coordinates.getHeight() + coordinates.getLatitude() + coordinates.getLongitude();

        return WEATHER.get(Math.abs(num) % 4);
    }
}
