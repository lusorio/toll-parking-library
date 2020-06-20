package com.parking.tollparkinglibrary.repositories;

import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class IParkingSlotRepositoryTest extends AbstractTest
{
    @Autowired
    private IParkingSlotRepository repository;

    @Test
    void findAllByTypeAndAvailableTrue()
    {
        var combustionSlots = repository.findAllByTypeAndAvailableTrue(ParkingSlotTypeEnum.COMBUSTION);
        assertAll(
                () -> assertNotNull(combustionSlots),
                () -> assertEquals(2, combustionSlots.size()));

        var e20kwSlots = repository.findAllByTypeAndAvailableTrue(ParkingSlotTypeEnum.ELECTRIC_20KW);
        assertAll(
                () -> assertNotNull(e20kwSlots),
                () -> assertEquals(3, e20kwSlots.size()));

        var e50kwSlots = repository.findAllByTypeAndAvailableTrue(ParkingSlotTypeEnum.ELECTRIC_50KW);
        assertAll(
                () -> assertNotNull(e50kwSlots),
                () -> assertEquals(3, e50kwSlots.size()));
    }
}