package com.danil.transport;

import com.danil.transport.model.Locomotive;
import com.danil.transport.model.PassengerCarriage;
import com.danil.transport.model.PassengerTrain;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PassengerTrainMockitoTest {
    @Test
    void shouldCalculatePassengersUsingMockedCarriages() {
        PassengerTrain train = new PassengerTrain(
                "TEST-1", new Locomotive("Test locomotive", 70.0, 4000));
        PassengerCarriage first = mock(PassengerCarriage.class);
        PassengerCarriage second = mock(PassengerCarriage.class);

        when(first.getPassengerCapacity()).thenReturn(30);
        when(second.getPassengerCapacity()).thenReturn(20);
        when(first.getBaggageCapacity()).thenReturn(10);
        when(second.getBaggageCapacity()).thenReturn(8);

        train.addCarriage(first);
        train.addCarriage(second);

        assertEquals(50, train.calculateTotalPassengers());
        assertEquals(18, train.calculateTotalBaggage());
    }
}
