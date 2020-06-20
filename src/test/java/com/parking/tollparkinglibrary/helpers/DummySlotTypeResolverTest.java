package com.parking.tollparkinglibrary.helpers;

import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DummySlotTypeResolverTest extends AbstractTest
{
    @Autowired
    private IParkingSlotTypeResolver resolver;

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName(value = "Test the random parking slot type assignation when the license plate is not recognised")
    void resolveParkingSlotTypeWhenUnknownVehicleType(String licensePlateNumber)
    {
        assertEquals(ParkingSlotTypeEnum.COMBUSTION, resolver.resolveParkingSlotType(licensePlateNumber));
    }

    @Test
    @DisplayName(value = "Test the random parking slot type assignation when the license plate is recognised")
    void resolveParkingSlotTypeWhenOK()
    {
        for (var i = 0; i < 100; i++)
        {
            assertTrue(List.of(ParkingSlotTypeEnum.values()).contains(resolver.resolveParkingSlotType(RandomStringUtils.random(10))));
        }
    }
}