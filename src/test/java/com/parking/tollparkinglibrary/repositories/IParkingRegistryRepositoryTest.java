package com.parking.tollparkinglibrary.repositories;

import com.parking.tollparkinglibrary.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IParkingRegistryRepositoryTest extends AbstractTest
{

    @Autowired
    private IParkingRegistryRepository repository;

    @Test
    void findOneByLicensePlateNumberAndOutIsNull_Error()
    {
        assertAll(
                () -> assertTrue(repository.findOneByLicensePlateNumberAndOutIsNull("AA-111-AA").isPresent()),
                () -> assertTrue(repository.findOneByLicensePlateNumberAndOutIsNull("BB-999-ZZ").isEmpty()));
    }
}