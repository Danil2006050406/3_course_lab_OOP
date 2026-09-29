package com.danil.transport;

import com.danil.transport.model.Locomotive;
import com.danil.transport.model.PassengerCarriage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RollingStockTest {
    @Test
    void shouldCreatePassengerCarriageWithCorrectProperties() {
        PassengerCarriage carriage = new PassengerCarriage("Comfort", 45.0, 40, 25, 4);

        assertEquals("Comfort", carriage.getName());
        assertEquals(45.0, carriage.getWeight());
        assertEquals(40, carriage.getPassengerCapacity());
        assertEquals(25, carriage.getBaggageCapacity());
        assertEquals(4, carriage.getComfortLevel());
        assertEquals("Passenger carriage", carriage.getType());
    }

    @Test
    void shouldCreateLocomotiveWithCorrectProperties() {
        Locomotive locomotive = new Locomotive("Electric", 80.0, 5000);

        assertEquals("Electric", locomotive.getName());
        assertEquals(80.0, locomotive.getWeight());
        assertEquals(5000, locomotive.getPower());
        assertEquals("Locomotive", locomotive.getType());
    }
}
