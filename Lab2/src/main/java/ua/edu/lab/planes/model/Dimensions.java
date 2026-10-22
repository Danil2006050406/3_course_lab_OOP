package ua.edu.lab.planes.model;

public record Dimensions(double length, double width, double height) {
    public Dimensions {
        if (!positive(length) || !positive(width) || !positive(height)) {
            throw new IllegalArgumentException("Габарити мають бути додатними");
        }
    }

    private static boolean positive(double value) {
        return Double.isFinite(value) && value > 0 && value <= 1000;
    }
}
