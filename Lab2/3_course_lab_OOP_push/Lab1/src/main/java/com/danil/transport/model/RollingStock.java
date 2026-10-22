package com.danil.transport.model;

public abstract class RollingStock {
    private final String name;
    private final double weight;

    protected RollingStock(String name, double weight) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.name = name;
        this.weight = weight;
    }

    public String getName() { return name; }
    public double getWeight() { return weight; }
    public abstract String getType();
}
