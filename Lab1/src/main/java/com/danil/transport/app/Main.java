package com.danil.transport.app;

import com.danil.transport.model.Locomotive;
import com.danil.transport.model.PassengerCarriage;
import com.danil.transport.model.PassengerTrain;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Locomotive locomotive = new Locomotive("InterCity Electric", 84.0, 5400);
        PassengerTrain train = new PassengerTrain("IC-101", locomotive);

        train.addCarriage(new PassengerCarriage("Economy 1", 42.0, 54, 30, 2));
        train.addCarriage(new PassengerCarriage("Comfort 1", 45.0, 40, 25, 4));
        train.addCarriage(new PassengerCarriage("Luxury 1", 48.0, 20, 15, 5));
        train.addCarriage(new PassengerCarriage("Economy 2", 42.0, 56, 32, 2));

        System.out.println("Passenger train: " + train.getNumber());
        System.out.println("Locomotive: " + locomotive.getName());
        System.out.println("Total passengers: " + train.calculateTotalPassengers());
        System.out.println("Total baggage: " + train.calculateTotalBaggage());

        System.out.println("\nCarriages sorted by comfort:");
        train.sortByComfort().forEach(carriage -> System.out.printf(
                "%s - comfort %d, passengers %d, baggage %d%n",
                carriage.getName(), carriage.getComfortLevel(),
                carriage.getPassengerCount(), carriage.getBaggageCount()));

        int minPassengers = 20;
        int maxPassengers = 45;
        System.out.printf("\nCarriages with %d-%d passengers:%n", minPassengers, maxPassengers);
        train.findByPassengerRange(minPassengers, maxPassengers)
                .forEach(carriage -> System.out.println(carriage.getName()));
    }
}
