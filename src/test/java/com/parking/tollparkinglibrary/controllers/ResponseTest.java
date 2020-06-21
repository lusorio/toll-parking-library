package com.parking.tollparkinglibrary.controllers;

import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest extends AbstractTest
{

    private Response<String> response;

    @BeforeEach
    private void init()
    {
        this.response = new Response<>();
    }

    @Test
    void construction()
    {
        assertAll(
                () -> assertNull(response.getStatus()),
                () -> assertNull(response.getContent()),
                () -> assertNull(response.getErrors()),
                () -> assertNull(response.getResult()),
                () -> assertNull(response.getResults()));
    }

    @Test
    void addResult()
    {
        response.addResult("test");

        assertAll(
                () -> assertNull(response.getStatus()),
                () -> assertNotNull(response.getContent()),
                () -> assertNull(response.getErrors()),
                () -> assertEquals("test", response.getResult()),
                () -> assertNull(response.getResults()));
    }

    @Test
    void addResults()
    {
        response.addResults(List.of("test 1", "test 2"));

        assertAll(
                () -> assertNull(response.getStatus()),
                () -> assertNotNull(response.getContent()),
                () -> assertNull(response.getErrors()),
                () -> assertNull(response.getResult()),
                () -> assertEquals(List.of("test 1", "test 2"), response.getResults()));
    }

    @Test
    void hasErrors()
    {
        assertFalse(response.hasErrors());

        response.addResult("test");
        assertFalse(response.hasErrors());

        response.addResults(List.of("test 1", "test 2"));
        assertFalse(response.hasErrors());

        response.addError(new BusinessException("exception"));
        assertTrue(response.hasErrors());
    }

    @Test
    void addError()
    {
        assertNull(response.getErrors());

        response.addError(new BusinessException("exception"));

        assertAll(
                () -> assertEquals(1, response.getErrors().size()),
                () -> assertEquals("exception", response.getErrors().get(0)));
    }
}