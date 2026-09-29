package com.danil.transport.model;

/** Passenger carriage with capacity, baggage capacity and comfort level. */
public class PassengerCarriage extends RollingStock {
    private final int passengerCapacity;
    private final int baggageCapacity;
    private final int comfortLevel;

    public PassengerCarriage(String name, double weight, int passengerCapacity,
                             int baggageCapacity, int comfortLevel) {
        super(name, weight);
        if (passengerCapacity < 0 || baggageCapacity < 0 || comfortLevel < 1) {
            throw new IllegalArgumentException("Invalid carriage parameters");
        }
        this.passengerCapacity = passengerCapacity;
        this.baggageCapacity = baggageCapacity;
        this.comfortLevel = comfortLevel;
    }

    public int getPassengerCapacity() { return passengerCapacity; }
    public int getBaggageCapacity() { return baggageCapacity; }
    public int getComfortLevel() { return comfortLevel; }

    @Override
    public String getType() { return "Passenger carriage"; }
}
