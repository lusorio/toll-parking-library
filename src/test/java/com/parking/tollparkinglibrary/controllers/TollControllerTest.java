package com.parking.tollparkinglibrary.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.exceptions.CheckinException;
import com.parking.tollparkinglibrary.exceptions.CheckoutException;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import com.parking.tollparkinglibrary.services.IParkingManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TollControllerTest extends AbstractTest
{
    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private TollController controller;

    @MockBean
    private IParkingManager manager;

    @BeforeEach
    void setup()
    {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        reset(manager);
    }

    @Nested
    @DisplayName("test thenReturn context")
    class testContext
    {
        @Test
        @DisplayName("test the controller is autowired")
        void contextLoads()
        {
            assertNotNull(controller);
        }
    }

    @Nested
    @DisplayName("test the GET#enterParking method")
    class testEnterParking
    {
        @Test
        void enterParking_Exception() throws Exception
        {
            var expectedException = new CheckinException("checkin exception");

            // service returns the list of entities
            Mockito.when(manager.checkIn(Mockito.anyString())).thenThrow(expectedException);

            // expected response
            var response = new Response<ParkingRegistry>();
            response.addError(expectedException);
            var jsonResponse = objectMapper.writeValueAsString(response);

            // execute call
            mockMvc.perform(get("/api/in").param("vehicleId", "AAA-111-ZZZ"))
                   .andExpect(status().isOk())
                   .andExpect(content().json(jsonResponse));
        }

        @Test
        void enterParking_OK() throws Exception
        {
            var expectedResult = new ParkingRegistry("AAA-1111-ZZ", new ParkingSlot(1L));

            // service returns the list of entities
            Mockito.when(manager.checkIn(Mockito.anyString())).thenReturn(expectedResult);

            // expected response
            var response = new Response<ParkingRegistry>();
            response.addResult(expectedResult);

            var jsonResponse = objectMapper.writeValueAsString(response);

            // execute call
            mockMvc.perform(get("/api/in").param("vehicleId", "AAA-111-ZZZ"))
                   .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("test the GET#exitParking method")
    class testExitLeaveParking
    {
        @Test
        void exitParking_Exception() throws Exception
        {
            var expectedException = new CheckoutException("checkout exception");

            // service returns the list of entities
            Mockito.when(manager.checkOut(Mockito.anyString())).thenThrow(expectedException);

            // expected response
            var response = new Response<ParkingRegistry>();
            response.addError(expectedException);
            var jsonResponse = objectMapper.writeValueAsString(response);

            // execute call
            mockMvc.perform(get("/api/out").param("vehicleId", "AAA-111-ZZZ"))
                   .andExpect(status().isOk())
                   .andExpect(content().json(jsonResponse));
        }

        @Test
        void exitParking_OK() throws Exception
        {
            var expectedResult = new ParkingRegistry("AAA-1111-ZZ", new ParkingSlot(1L));

            // service returns the list of entities
            Mockito.when(manager.checkOut(Mockito.anyString())).thenReturn(expectedResult);

            // expected response
            var response = new Response<ParkingRegistry>();
            response.addResult(expectedResult);
            var jsonResponse = objectMapper.writeValueAsString(response);

            // execute call
            mockMvc.perform(get("/api/out").param("vehicleId", "AAA-111-ZZZ"))
                   .andExpect(status().isOk());
        }
    }
}