package ru.fignigno.avax.fly;

import ru.fignigno.avax.exception.SimulationException;
import ru.fignigno.avax.fly.Coordinates;
import ru.fignigno.avax.fly.Flyable;
import ru.fignigno.avax.fly.Helicopter;

public class AircraftFactory {

    public static Flyable newAircraft(String type,
                                      String name,
                                      int longitude,
                                      int latitude,
                                      int height) {
        Coordinates coordinates = new Coordinates(longitude, latitude, height);
        if ("Helicopter".equals(type)) {
            return new Helicopter(name, coordinates);
        }
        if ("JetPlane".equals(type)) {
            return new JetPlane(name, coordinates);
        }
        if ("Balloon".equals(type)) {
            return new Balloon(name, coordinates);
        }
        throw new SimulationException("Unknown aircraft type - " + type);
    }
}
