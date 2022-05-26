package ru.fignigno.avax.fly;

import ru.fignigno.avax.WeatherType;
import ru.fignigno.avax.provider.WeatherProvider;
import ru.fignigno.avax.WeatherTower;

import java.util.Map;

public class JetPlane extends Aircraft implements Flyable{

    private static final Map<String, String> MESSAGES = Map.of(
            WeatherType.SUNNY, "I am a Khrushchev's corn plane",
            WeatherType.RAINY, "Is Tom Hanks in me?",
            WeatherType.FOGGY, "WHO VAPED???",
            WeatherType.SNOWY, "And it's so white as snoooow",
            GROUNDED, "Clap me I landed"
    );

    private WeatherTower weatherTower;
    private boolean isGrounded;

    public JetPlane(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    protected String getType() {
        return "JetPlane";
    }

    @Override
    protected void updateIfSunWeather() {
        int newLatitude = coordinates.getLatitude() + 10;
        int newHeight = coordinates.getHeight() + 2;
        if (newHeight > 100) {
            newHeight = 100;
        }
        this.coordinates = new Coordinates(coordinates.getLongitude(), newLatitude, newHeight);

    }

    @Override
    protected void updateIfRainWeather() {
        int newLatitude = coordinates.getLatitude() + 10;
        this.coordinates = new Coordinates(coordinates.getLongitude(), newLatitude, coordinates.getHeight());
    }

    @Override
    protected void updateIfFogWeather() {
        int newLatitude = coordinates.getLatitude() + 1;
        this.coordinates = new Coordinates(coordinates.getLongitude(), newLatitude, coordinates.getHeight());
    }

    @Override
    protected void updateIfSnowWeather() {
        int newLatitude = coordinates.getLatitude() + - 7;
        this.coordinates = new Coordinates(coordinates.getLongitude(), newLatitude, coordinates.getHeight());
    }

    @Override
    public void updateConditions() {
        this.updateCoordinates(weatherTower.getWeather(coordinates));
        if (isGrounded) {
            notifyUpdateCondition(MESSAGES.get(GROUNDED));
            weatherTower.unregister(this);
        } else {
            notifyUpdateCondition(MESSAGES.get(WeatherProvider.getWeatherProvider().getCurrentWeather(coordinates)));
        }
    }

    @Override
    public void registerTower(WeatherTower weatherTower) {
        weatherTower.register(this);
        this.weatherTower = weatherTower;
        this.notifyAboutTowerRegistration();
    }
}
