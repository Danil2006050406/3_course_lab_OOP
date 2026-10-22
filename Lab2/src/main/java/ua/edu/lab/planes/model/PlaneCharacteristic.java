package ua.edu.lab.planes.model;

import java.util.Objects;
import java.util.Optional;

public record PlaneCharacteristic(PlaneType type, int seats, Optional<Armament> armament) {
    public PlaneCharacteristic {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(armament, "armament");
        if (seats != 1 && seats != 2) {
            throw new IllegalArgumentException("Кількість місць має дорівнювати 1 або 2");
        }
        if (type == PlaneType.RECONNAISSANCE && armament.isPresent()) {
            throw new IllegalArgumentException("Розвідувальний літак не повинен мати боєкомплекту");
        }
        if (type != PlaneType.RECONNAISSANCE && armament.isEmpty()) {
            throw new IllegalArgumentException("Для цього типу літака потрібен боєкомплект");
        }
    }
}
