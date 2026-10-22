package com.danil.transport.model;

public class PassengerCarriage extends RollingStock {
    private final int passengerCount;
    private final int baggageCount;
    private final int comfortLevel;

    public PassengerCarriage(String name, double weight, int passengerCount,
                             int baggageCount, int comfortLevel) {
        super(name, weight);
        if (passengerCount < 0 || baggageCount < 0 || comfortLevel < 1) {
            throw new IllegalArgumentException("Invalid carriage parameters");
        }
        this.passengerCount = passengerCount;
        this.baggageCount = baggageCount;
        this.comfortLevel = comfortLevel;
    }

    public int getPassengerCount() { return passengerCount; }
    public int getBaggageCount() { return baggageCount; }
    public int getComfortLevel() { return comfortLevel; }

    @Override
    public String getType() { return "Passenger carriage"; }
}
