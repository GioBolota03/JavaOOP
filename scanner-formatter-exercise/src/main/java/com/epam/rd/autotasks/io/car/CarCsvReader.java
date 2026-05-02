package com.epam.rd.autotasks.io.car;

import com.epam.rd.autotasks.io.car.model.Car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarCsvReader {

    public List<Car> readCars(File file) {
        List<Car> cars = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(",");

                String brand = scanner.next();
                String model = scanner.next();
                int capacity = Integer.parseInt(scanner.next());
                int performance = Integer.parseInt(scanner.next());
                double acceleration = Double.parseDouble(scanner.next());

                cars.add(new Car(brand, model, capacity, performance, acceleration));
                scanner.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return cars;
    }
}