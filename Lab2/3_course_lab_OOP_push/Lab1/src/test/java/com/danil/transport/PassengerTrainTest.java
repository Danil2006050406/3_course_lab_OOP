package com.danil.transport;

import com.danil.transport.model.Locomotive;
import com.danil.transport.model.PassengerCarriage;
import com.danil.transport.model.PassengerTrain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PassengerTrainTest {
    private PassengerTrain train;

    @BeforeEach
    void setUp() {
        train = new PassengerTrain("IC-101", new Locomotive("Electric", 80.0, 5000));
        train.addCarriage(new PassengerCarriage("Economy", 40.0, 54, 30, 2));
        train.addCarriage(new PassengerCarriage("Comfort", 45.0, 40, 25, 4));
        train.addCarriage(new PassengerCarriage("Luxury", 48.0, 20, 15, 5));
    }

    @Test
    void shouldCalculateTotalPassengers() {
        assertEquals(114, train.calculateTotalPassengers());
    }

    @Test
    void shouldCalculateTotalBaggage() {
        assertEquals(70, train.calculateTotalBaggage());
    }

    @Test
    void shouldSortCarriagesByComfortDescending() {
        List<PassengerCarriage> sorted = train.sortByComfort();
        assertEquals("Luxury", sorted.get(0).getName());
        assertEquals("Comfort", sorted.get(1).getName());
        assertEquals("Economy", sorted.get(2).getName());
    }

    @Test
    void shouldFindCarriagesByPassengerRange() {
        List<PassengerCarriage> result = train.findByPassengerRange(20, 45);
        assertEquals(2, result.size());
        assertEquals("Comfort", result.get(0).getName());
        assertEquals("Luxury", result.get(1).getName());
    }

    @Test
    void shouldRejectInvalidPassengerRange() {
        assertThrows(IllegalArgumentException.class,
                () -> train.findByPassengerRange(50, 20));
    }
}
