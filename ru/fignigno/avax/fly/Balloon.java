package ru.fignigno.avax.fly;

import ru.fignigno.avax.WeatherType;
import ru.fignigno.avax.provider.WeatherProvider;
import ru.fignigno.avax.WeatherTower;

import java.util.Map;

public class Balloon extends Aircraft implements Flyable {

    private static final Map<String, String>  MESSAGES = Map.of(
            WeatherType.SUNNY, "I believe I can fly",
            WeatherType.RAINY, "Hello darkness, my old friend",
            WeatherType.FOGGY, "I am a red balloon from IT",
            WeatherType.SNOWY, "I don't know, what I am doing here",
            GROUNDED, "Ah s**t, here we come again"
    );

    private WeatherTower weatherTower;
    private boolean isGrounded;

    public Balloon(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    protected String getType() {
        return "Balloon";
    }

    @Override
    protected void updateIfSunWeather() {
        int newLongitude = coordinates.getLongitude() + 2;
        int newHeight = coordinates.getHeight() + 4;
        if (newHeight > 100) {
            newHeight = 100;
        }
        this.coordinates = new Coordinates(
                newLongitude,
                coordinates.getLatitude(),
                newHeight
        );
    }

    @Override
    protected void updateIfRainWeather() {
        int newHeight = coordinates.getHeight() - 5;
        if (newHeight <= 0) {
            isGrounded = true;
        }
        this.coordinates = new Coordinates(
                coordinates.getLongitude(),
                coordinates.getLatitude(),
                newHeight
        );
    }

    @Override
    protected void updateIfFogWeather() {
        int newHeight = coordinates.getHeight() - 3;
        if (newHeight <= 0) {
            isGrounded = true;
        }
        this.coordinates = new Coordinates(
                coordinates.getLongitude(),
                coordinates.getLatitude(),
                newHeight
        );
    }

    @Override
    protected void updateIfSnowWeather() {
        int newHeight = coordinates.getHeight() - 15;
        if (newHeight <= 0) {
            isGrounded = true;
        }
        this.coordinates = new Coordinates(
                coordinates.getLongitude(),
                coordinates.getLatitude(),
                newHeight
        );
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
