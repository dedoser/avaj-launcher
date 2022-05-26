package ru.fignigno.avax;

import ru.fignigno.avax.exception.SimulationException;
import ru.fignigno.avax.fly.AircraftFactory;
import ru.fignigno.avax.fly.Flyable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulator {

    private static final String SCENARIO_FILENAME = "simulation.txt";

    private static List<String> infoLines = new ArrayList<>();
    private static int simAmount;
    private static WeatherTower weatherTower;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Wrong amount of arguments");
            return;
        }
        initScenarioFile();
        try {
            readPropertiesFromFile(args[0]);
            initVariables();
        } catch (SimulationException e) {
            System.err.println(e.getMessage());
            return;
        }
        for (int i = 0; i < simAmount; ++i) {
            weatherTower.changeWeather();
        }
    }

    private static void initScenarioFile() {
        try {
            File outFile = new File(SCENARIO_FILENAME);
            outFile.createNewFile();
            PrintStream newOutStream = new PrintStream(outFile);
            System.setOut(newOutStream);
        } catch (IOException e) {
            System.err.println("Cannot write to file");
        }

    }

    private static void readPropertiesFromFile(String filename) {
        File file = new File(filename);
        try (Scanner scanner = new Scanner(file)) {
            String sim = scanner.nextLine();
            simAmount = Integer.parseInt(sim);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    infoLines.add(line);
                }
            }
        } catch (NullPointerException | FileNotFoundException | NumberFormatException e) {
            throw new SimulationException(e);
        }
    }

    private static void initVariables() {
        weatherTower = new WeatherTower();
        infoLines.forEach(Simulator::handleInfoLine);
    }

    private static void handleInfoLine(String line) {
        String[] elems = line.split(" ");
        if (elems.length != 5) {
            throw new SimulationException("Not enough information about aircraft");
        }
        try {
            Flyable flyable = AircraftFactory.newAircraft(
                    elems[0],
                    elems[1],
                    Integer.parseInt(elems[2]),
                    Integer.parseInt(elems[3]),
                    Integer.parseInt(elems[4])
            );
            flyable.registerTower(weatherTower);
        } catch (NumberFormatException e) {
            throw new SimulationException(e);
        }
    }
}
