package com.danil.transport.model;

/** Locomotive of a passenger train. */
public class Locomotive extends RollingStock {
    private final int power;

    public Locomotive(String name, double weight, int power) {
        super(name, weight);
        if (power <= 0) {
            throw new IllegalArgumentException("Power must be positive");
        }
        this.power = power;
    }

    public int getPower() { return power; }

    @Override
    public String getType() { return "Locomotive"; }
}
