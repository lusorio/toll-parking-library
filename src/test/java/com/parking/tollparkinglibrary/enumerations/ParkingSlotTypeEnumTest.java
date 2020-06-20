package com.parking.tollparkinglibrary.enumerations;

import com.parking.tollparkinglibrary.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingSlotTypeEnumTest extends AbstractTest
{
    @Test
    void testValues()
    {
        assertEquals(3, ParkingSlotTypeEnum.values().length);
    }

    @Test
    void getPowerSupply()
    {
        assertAll(
                () -> assertEquals(0, ParkingSlotTypeEnum.COMBUSTION.getPowerSupply()),
                () -> assertEquals(20, ParkingSlotTypeEnum.ELECTRIC_20KW.getPowerSupply()),
                () -> assertEquals(50, ParkingSlotTypeEnum.ELECTRIC_50KW.getPowerSupply()));
    }

    @Test
    void hasPowerSupply()
    {
        assertAll(
                () -> assertFalse(ParkingSlotTypeEnum.COMBUSTION.hasPowerSupply()),
                () -> assertTrue(ParkingSlotTypeEnum.ELECTRIC_20KW.hasPowerSupply()),
                () -> assertTrue(ParkingSlotTypeEnum.ELECTRIC_50KW.hasPowerSupply()));
    }
}