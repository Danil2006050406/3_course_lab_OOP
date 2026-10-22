package ua.edu.lab.planes.service;

import ua.edu.lab.planes.model.Aircraft;
import java.util.Comparator;

public final class AircraftSorters {
    private AircraftSorters() {}

    public static final Comparator<Aircraft> BY_MODEL =
            Comparator.comparing(Aircraft::model, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Aircraft> BY_ORIGIN =
            Comparator.comparing(Aircraft::origin, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Aircraft::model, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Aircraft> BY_PRICE =
            Comparator.comparing(Aircraft::price);
}
