package com.parking.tollparkinglibrary.providers.parkingratepolicy;

import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedRatePolicyProviderTest extends AbstractTest
{
    FixedRatePolicyProvider provider;

    @BeforeEach
    void init()
    {
        provider = new FixedRatePolicyProvider("3.5");
    }

    @ParameterizedTest
    @NullSource
    void calculateTotal_InvalidRegistry(Long minutesSpent)
    {
        var registry = new ParkingRegistry();
        registry.setId(1L);
        registry.setLicensePlateNumber("ABC");
        registry.setSlot(new ParkingSlot(1L));
        registry.setOut(null);
        registry.setIn(null);

        assertThrows(IllegalArgumentException.class, () -> provider.calculateTotal(registry));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 10, 60, 180, 1440})
    void calculateTotal_OK(Long minutesSpent)
    {
        var registry = new ParkingRegistry();
        registry.setId(1L);
        registry.setLicensePlateNumber("ABC");
        registry.setSlot(new ParkingSlot(1L));
        registry.setOut(LocalDateTime.now());
        registry.setIn(registry.getOut().minusMinutes(minutesSpent));

        assertEquals(BigDecimal.valueOf(3.50).setScale(2, RoundingMode.UNNECESSARY), provider.calculateTotal(registry));
    }
}