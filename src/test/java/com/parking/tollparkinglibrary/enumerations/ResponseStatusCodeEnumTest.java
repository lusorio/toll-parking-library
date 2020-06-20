package com.parking.tollparkinglibrary.enumerations;

import com.parking.tollparkinglibrary.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseStatusCodeEnumTest extends AbstractTest
{
    @Test
    void testValues()
    {
        assertEquals(2, ResponseStatusCodeEnum.values().length);
    }

    @Test
    void get()
    {
        assertAll(
                () -> assertEquals(0, ResponseStatusCodeEnum.SUCCESS.get()),
                () -> assertEquals(1, ResponseStatusCodeEnum.FAILURE.get()));
    }

    @Test
    void isSuccess()
    {
        assertAll(
                () -> assertTrue(ResponseStatusCodeEnum.SUCCESS.isSuccess()),
                () -> assertFalse(ResponseStatusCodeEnum.FAILURE.isSuccess()));
    }

    @Test
    void isFailure()
    {
        assertAll(
                () -> assertFalse(ResponseStatusCodeEnum.SUCCESS.isFailure()),
                () -> assertTrue(ResponseStatusCodeEnum.FAILURE.isFailure()));
    }
}