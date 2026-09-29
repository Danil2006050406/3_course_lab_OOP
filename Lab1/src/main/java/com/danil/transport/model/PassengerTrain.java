package com.danil.transport.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PassengerTrain {
    private final String number;
    private final Locomotive locomotive;
    private final List<PassengerCarriage> carriages = new ArrayList<>();

    public PassengerTrain(String number, Locomotive locomotive) {
        if (number == null || number.isBlank() || locomotive == null) {
            throw new IllegalArgumentException("Invalid train parameters");
        }
        this.number = number;
        this.locomotive = locomotive;
    }

    public String getNumber() { return number; }
    public Locomotive getLocomotive() { return locomotive; }

    public void addCarriage(PassengerCarriage carriage) {
        if (carriage == null) {
            throw new IllegalArgumentException("Carriage cannot be null");
        }
        carriages.add(carriage);
    }

    public List<PassengerCarriage> getCarriages() {
        return List.copyOf(carriages);
    }

    public int calculateTotalPassengers() {
        return carriages.stream()
                .mapToInt(PassengerCarriage::getPassengerCount)
                .sum();
    }

    public int calculateTotalBaggage() {
        return carriages.stream()
                .mapToInt(PassengerCarriage::getBaggageCount)
                .sum();
    }

    public List<PassengerCarriage> sortByComfort() {
        return carriages.stream()
                .sorted(Comparator.comparingInt(PassengerCarriage::getComfortLevel).reversed())
                .collect(Collectors.toList());
    }

    public List<PassengerCarriage> findByPassengerRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum cannot exceed maximum");
        }
        return carriages.stream()
                .filter(carriage -> carriage.getPassengerCount() >= min
                        && carriage.getPassengerCount() <= max)
                .collect(Collectors.toList());
    }
}
