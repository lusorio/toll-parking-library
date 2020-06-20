package com.parking.tollparkinglibrary.repositories;

import com.parking.tollparkinglibrary.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class IParkingRegistryRepositoryTest extends AbstractTest
{

    @Autowired
    private IParkingRegistryRepository repository;

    @Test
    void findOneByLicensePlateNumberAndOutIsNull_Error()
    {
        assertAll(
                () -> assertTrue(repository.findOneByLicensePlateNumberAndOutIsNull("ABC").isEmpty()),
                () -> assertThrows(SQLException.class, () -> repository.findOneByLicensePlateNumberAndOutIsNull("ABC-123")));
    }

    @Test
    void findOneByLicensePlateNumberAndOutIsNull_OK()
    {
        var registry = repository.findOneByLicensePlateNumberAndOutIsNull("XYZ-123");
        assertAll(
                () -> assertTrue(registry.isPresent()),
                () -> assertEquals("XYZ-123", registry.get().getLicensePlateNumber()));
    }
}