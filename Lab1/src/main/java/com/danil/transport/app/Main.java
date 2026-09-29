package com.danil.transport.app;

import com.danil.transport.model.Locomotive;
import com.danil.transport.model.PassengerCarriage;
import com.danil.transport.model.PassengerTrain;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Locomotive locomotive = new Locomotive("Sirovartranc", 84.0, 5400);
        PassengerTrain train = new PassengerTrain("ST-666", locomotive);

        Locomotive secondLocomotive = new Locomotive("Fafko Express", 86.0, 5000);
        PassengerTrain secondTrain = new PassengerTrain("FF-777", secondLocomotive);

        train.addCarriage(new PassengerCarriage("Economy 1", 42.0, 54, 30, 2));
        train.addCarriage(new PassengerCarriage("Comfort 1", 45.0, 40, 25, 4));
        train.addCarriage(new PassengerCarriage("Luxury 1", 48.0, 20, 15, 5));
        train.addCarriage(new PassengerCarriage("Economy 2", 42.0, 56, 32, 2));

        secondTrain.addCarriage(new PassengerCarriage("Sleeping 1", 45.0, 30, 20, 5));
        secondTrain.addCarriage(new PassengerCarriage("Sleeping 2", 43.5, 15, 10, 3));

        System.out.println("First train: " + train.getNumber());
        System.out.println("Locomotive: " + locomotive.getName());
        System.out.println("Total passengers: " + train.calculateTotalPassengers());
        System.out.println("Total baggage: " + train.calculateTotalBaggage());

        System.out.println("\nCarriages sorted by comfort:");
        train.sortByComfort().forEach(carriage -> System.out.printf(
                "%s - comfort %d, passengers %d, baggage %d%n",
                carriage.getName(),
                carriage.getComfortLevel(),
                carriage.getPassengerCount(),
                carriage.getBaggageCount()
        ));

        int minPassengers = 20;
        int maxPassengers = 45;

        System.out.printf(
                "\nCarriages with %d-%d passengers:%n",
                minPassengers,
                maxPassengers
        );

        train.findByPassengerRange(minPassengers, maxPassengers)
                .forEach(carriage -> System.out.println(carriage.getName()));


        System.out.println("\n==============================\n");


        System.out.println("Second train: " + secondTrain.getNumber());
        System.out.println("Locomotive: " + secondTrain.getLocomotive().getName());
        System.out.println("Total passengers: " + secondTrain.calculateTotalPassengers());
        System.out.println("Total baggage: " + secondTrain.calculateTotalBaggage());

        System.out.println("\nCarriages sorted by comfort:");
        secondTrain.sortByComfort().forEach(carriage -> System.out.printf(
                "%s - comfort %d, passengers %d, baggage %d%n",
                carriage.getName(),
                carriage.getComfortLevel(),
                carriage.getPassengerCount(),
                carriage.getBaggageCount()
        ));

        System.out.printf(
                "\nCarriages with %d-%d passengers:%n",
                minPassengers,
                maxPassengers
        );

        secondTrain.findByPassengerRange(minPassengers, maxPassengers)
                .forEach(carriage -> System.out.println(carriage.getName()));
            }
}
