package ru.fignigno.avax.fly;

import ru.fignigno.avax.WeatherType;
import ru.fignigno.avax.provider.WeatherProvider;
import ru.fignigno.avax.WeatherTower;

import java.util.Map;

public class Helicopter extends Aircraft implements Flyable {

    private static final Map<String, String> MESSAGES = Map.of(
            WeatherType.SUNNY, "ETO VERTOLYOT",
            WeatherType.RAINY, "Have you ever seen the rain!",
            WeatherType.FOGGY, "It looks like a helicopter mission from GTA Vice City",
            WeatherType.SNOWY, "Snow patrol tututu",
            GROUNDED, "Let the bodies hit the floor"
    );

    private WeatherTower weatherTower;
    private boolean isGrounded;

    public Helicopter(String name, Coordinates coordinates) {
        super(name, coordinates);
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

    @Override
    protected String getType() {
        return "Helicopter";
    }

    @Override
    protected void updateIfSunWeather() {
        int newHeight = coordinates.getHeight() + 2;
        int newLongitude = coordinates.getLongitude() + 10;
        if (newHeight > 100) {
            newHeight = 100;
        }
        this.coordinates = new Coordinates(newLongitude, coordinates.getLatitude(), newHeight);
    }

    @Override
    protected void updateIfRainWeather() {
        this.coordinates = new Coordinates(
                coordinates.getLongitude() + 5,
                coordinates.getLatitude(),
                coordinates.getHeight()
        );
    }

    @Override
    protected void updateIfFogWeather() {
        this.coordinates = new Coordinates(
                coordinates.getLongitude() + 1,
                coordinates.getLatitude(),
                coordinates.getHeight()
        );
    }

    @Override
    protected void updateIfSnowWeather() {
        int newHeight = coordinates.getHeight() - 12;
        if (newHeight <= 0) {
            isGrounded = true;
        }
        this.coordinates = new Coordinates(
                coordinates.getLongitude(),
                coordinates.getLatitude(),
                newHeight
        );
    }
}
