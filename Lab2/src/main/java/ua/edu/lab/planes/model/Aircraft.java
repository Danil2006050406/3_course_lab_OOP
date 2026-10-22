package ua.edu.lab.planes.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record Aircraft(
        String id,
        String model,
        String origin,
        List<PlaneCharacteristic> characteristics,
        Dimensions parameters,
        BigDecimal price) {

    public Aircraft {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID обов'язковий");
        if (model == null || model.isBlank()) throw new IllegalArgumentException("Model обов'язковий");
        if (origin == null || origin.isBlank()) throw new IllegalArgumentException("Origin обов'язковий");
        Objects.requireNonNull(characteristics, "characteristics");
        Objects.requireNonNull(parameters, "parameters");
        Objects.requireNonNull(price, "price");
        if (characteristics.size() < 2) throw new IllegalArgumentException("Потрібно щонайменше дві характеристики");
        if (price.signum() < 0 || price.compareTo(new BigDecimal("1000000000000")) > 0) {
            throw new IllegalArgumentException("Ціна має бути в межах 0..1000000000000");
        }
        characteristics = List.copyOf(characteristics);
    }
}
