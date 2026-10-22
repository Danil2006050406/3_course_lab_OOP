package ua.edu.lab.planes.model;

public record Armament(int missiles, boolean radar) {
    public Armament {
        if (missiles < 0 || missiles > 10) {
            throw new IllegalArgumentException("Кількість ракет має бути в межах 0..10");
        }
    }
}
