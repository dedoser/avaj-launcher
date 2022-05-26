package ru.fignigno.avax.fly;

import ru.fignigno.avax.WeatherType;
import ru.fignigno.avax.exception.SimulationException;

import java.util.Map;

public abstract class Aircraft {
    protected long id;
    protected String name;
    protected Coordinates coordinates;
    private boolean isGrounded = false;

    private static long idCounter;
    private final Map<String, Runnable> COORD_UPDATES = Map.of(
            WeatherType.SUNNY, this::updateIfSunWeather,
            WeatherType.FOGGY, this::updateIfFogWeather,
            WeatherType.RAINY, this::updateIfRainWeather,
            WeatherType.SNOWY, this::updateIfSnowWeather
    );
    protected static final String GROUNDED = "GROUNDED";


    protected Aircraft(String name, Coordinates coordinates) {
        this.id = nextId();
        this.name = name;
        this.coordinates = coordinates;
    }

    private long nextId() {
        return idCounter++;
    }

    protected void updateCoordinates(String weatherType) {
        if (!COORD_UPDATES.containsKey(weatherType)) {
            throw new SimulationException("Unrecognized weather type - " + weatherType);
        }
        COORD_UPDATES.get(weatherType).run();
    }

    protected void notifyAboutTowerRegistration() {
        System.out.printf("Tower says: %s#%s(%d) registered to weather tower.%n", getType(), name, id);
    }

    protected void notifyUpdateCondition(String message) {
        System.out.printf("%s#%s(%d): %s\n", this.getType(), this.name, this.id, message);
    }

    protected abstract String getType();

    protected abstract void updateIfSunWeather();
    protected abstract void updateIfRainWeather();
    protected abstract void updateIfFogWeather();
    protected abstract void updateIfSnowWeather();

}
