package com.parking.tollparkinglibrary.controllers;

import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.enumerations.ResponseStatusCodeEnum;
import com.parking.tollparkinglibrary.exceptions.BusinessException;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AbstractControllerTest extends AbstractTest
{
    @Autowired
    private TollController abstractController;

    @Test
    void getResponse()
    {
        // single result

        var responseString = abstractController.getResponse("test");
        assertAll(
                () -> assertNotNull(responseString),
                () -> assertEquals(ResponseStatusCodeEnum.SUCCESS, responseString.getStatus()),
                () -> assertNotNull(responseString.getContent()),
                () -> assertEquals("test", responseString.getResult()),
                () -> assertNull(responseString.getResults()),
                () -> assertNull(responseString.getErrors()));

        var responseLong = abstractController.getResponse(2L);
        assertAll(
                () -> assertNotNull(responseLong),
                () -> assertEquals(ResponseStatusCodeEnum.SUCCESS, responseLong.getStatus()),
                () -> assertNotNull(responseLong.getContent()),
                () -> assertEquals(2L, responseLong.getResult()),
                () -> assertNull(responseLong.getResults()),
                () -> assertNull(responseLong.getErrors()));

        var resultObject = new ParkingRegistry("ABC-111-XX", new ParkingSlot(1L));
        var responseObject = abstractController.getResponse(resultObject);
        assertAll(
                () -> assertNotNull(responseObject),
                () -> assertEquals(ResponseStatusCodeEnum.SUCCESS, responseObject.getStatus()),
                () -> assertNotNull(responseObject.getContent()),
                () -> assertEquals(resultObject, responseObject.getResult()),
                () -> assertNull(responseObject.getResults()),
                () -> assertNull(responseObject.getErrors()));

        // multiple result

        var responseStringList = abstractController.getResponse(List.of("test1", "test2"));
        assertAll(
                () -> assertNotNull(responseStringList),
                () -> assertEquals(ResponseStatusCodeEnum.SUCCESS, responseStringList.getStatus()),
                () -> assertNotNull(responseStringList.getContent()),
                () -> assertNull(responseStringList.getResult()),
                () -> assertEquals(List.of("test1", "test2"), responseStringList.getResults()),
                () -> assertNull(responseStringList.getErrors()));

        var responseLongList = abstractController.getResponse(List.of(2L, 10L));
        assertAll(
                () -> assertNotNull(responseLongList),
                () -> assertEquals(ResponseStatusCodeEnum.SUCCESS, responseLongList.getStatus()),
                () -> assertNotNull(responseLongList.getContent()),
                () -> assertNull(responseLongList.getResult()),
                () -> assertEquals(List.of(2L, 10L), responseLongList.getResults()),
                () -> assertNull(responseLongList.getErrors()));

        var resultObjectList = List.of(
                new ParkingRegistry("AAA-111-XX", new ParkingSlot(1L)),
                new ParkingRegistry("BBB-222-YY", new ParkingSlot(2L)));

        var responseObjectList = abstractController.getResponse(resultObjectList);
        assertAll(
                () -> assertNotNull(responseObjectList),
                () -> assertEquals(ResponseStatusCodeEnum.SUCCESS, responseObjectList.getStatus()),
                () -> assertNotNull(responseObjectList.getContent()),
                () -> assertNull(responseObjectList.getResult()),
                () -> assertEquals(resultObjectList, responseObjectList.getResults()),
                () -> assertNull(responseObjectList.getErrors()));
    }

    @Test
    void getResponseError()
    {
        // single result

        var exception = new BusinessException("business exception");
        var responseException = abstractController.getResponseError(exception);
        assertAll(
                () -> assertNotNull(responseException),
                () -> assertEquals(ResponseStatusCodeEnum.FAILURE, responseException.getStatus()),
                () -> assertNull(responseException.getResult()),
                () -> assertNull(responseException.getResults()),
                () -> assertEquals(1, responseException.getErrors().size()),
                () -> assertEquals(exception.getMessage(), responseException.getErrors().get(0)));

        // multiple result

        var exceptionList = List.of(
                new BusinessException("business exception 1"),
                new BusinessException("business exception 2"));

        var responseExceptionList = abstractController.getResponseError(exceptionList);
        assertAll(
                () -> assertNotNull(responseExceptionList),
                () -> assertEquals(ResponseStatusCodeEnum.FAILURE, responseExceptionList.getStatus()),
                () -> assertNull(responseExceptionList.getResult()),
                () -> assertNull(responseExceptionList.getResults()),
                () -> assertEquals(2, responseExceptionList.getErrors().size()),
                () -> assertEquals(responseExceptionList.getErrors(),
                                   exceptionList.stream().map(Throwable::getMessage).collect(Collectors.toList())));
    }

}