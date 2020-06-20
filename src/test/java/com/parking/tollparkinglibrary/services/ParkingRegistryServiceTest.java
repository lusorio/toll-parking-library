package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.exceptions.BusinessException;
import com.parking.tollparkinglibrary.exceptions.NoSuchRegistryException;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import com.parking.tollparkinglibrary.providers.parkingratepolicy.FixedRatePolicyProvider;
import com.parking.tollparkinglibrary.repositories.IParkingRegistryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class ParkingRegistryServiceTest extends AbstractTest
{
    @Mock
    private IParkingRegistryRepository repository;

    @Mock
    private FixedRatePolicyProvider ratePolicyProvider;

    @InjectMocks
    private ParkingRegistryService service;

    @Nested
    @DisplayName("Test the initParkingRegistry method")
    class InitParkingRegistry
    {
        @Test
        @DisplayName("Assert that if an ongoing registry exist for the vehicle id a BusinessException is thrown")
        void initParkingRegistry_Error_DuplicatedRegistry()
        {
            var licensePlateNumber = "ABC";
            var parkingSlot = new ParkingSlot(1L);

            Mockito.when(repository.findOneByLicensePlateNumberAndOutIsNull(anyString())).thenReturn(Optional.of(new ParkingRegistry()));

            assertThrows(BusinessException.class, () -> service.initParkingRegistry(licensePlateNumber, parkingSlot));

            Mockito.verify(repository, Mockito.times(1)).findOneByLicensePlateNumberAndOutIsNull(licensePlateNumber);
            Mockito.verifyNoMoreInteractions(repository);
            Mockito.verifyNoInteractions(ratePolicyProvider);
        }

        @Test
        @DisplayName("Assert that a new registry is created")
        void initParkingRegistry_OK() throws BusinessException
        {
            var licensePlateNumber = "ABC";
            var parkingSlot = new ParkingSlot(1L);
            var parkingRegistry = new ParkingRegistry(licensePlateNumber, parkingSlot);

            Mockito.when(repository.findOneByLicensePlateNumberAndOutIsNull(anyString())).thenReturn(Optional.empty());
            Mockito.when(repository.saveAndFlush(any(ParkingRegistry.class))).thenReturn(parkingRegistry);

            var result = service.initParkingRegistry(licensePlateNumber, parkingSlot);

            assertEquals(parkingRegistry, result);

            Mockito.verify(repository, Mockito.times(1)).findOneByLicensePlateNumberAndOutIsNull(licensePlateNumber);
            Mockito.verify(repository, Mockito.times(1)).saveAndFlush(any(ParkingRegistry.class));
            Mockito.verifyNoMoreInteractions(repository);

            Mockito.verifyNoInteractions(ratePolicyProvider);
        }
    }

    @Nested
    @DisplayName("Test the closeParkingRegistry method")
    class CloseParkingRegistry
    {
        @ParameterizedTest
        @ValueSource(strings = "ABC")
        @NullAndEmptySource
        @DisplayName("Assert that a NoSuchRegistryException is thrown if the vehicle id does not have an ongoing registry")
        void closeParkingRegistry_Error_RegistryNotFound(String vehicleId)
        {
            Mockito.when(repository.findOneByLicensePlateNumberAndOutIsNull(vehicleId)).thenReturn(Optional.empty());

            assertThrows(NoSuchRegistryException.class, () -> service.closeParkingRegistry(vehicleId));

            Mockito.verify(repository, Mockito.times(1)).findOneByLicensePlateNumberAndOutIsNull(vehicleId);
            Mockito.verifyNoMoreInteractions(repository);

            Mockito.verifyNoInteractions(ratePolicyProvider);
        }

        @Test
        @DisplayName("Assert that a registry is closed")
        void closeParkingRegistry_OK() throws BusinessException
        {
            var licensePlateNumber = "ABC";
            var parkingSlot = new ParkingSlot(1L);
            var parkingRegistry = new ParkingRegistry(licensePlateNumber, parkingSlot);

            Mockito.when(repository.findOneByLicensePlateNumberAndOutIsNull(anyString())).thenReturn(Optional.of(parkingRegistry));
            Mockito.when(repository.saveAndFlush(any(ParkingRegistry.class))).thenReturn(parkingRegistry);
            Mockito.when(ratePolicyProvider.calculateTotal(any(ParkingRegistry.class))).thenReturn(BigDecimal.ONE);

            var result = service.closeParkingRegistry(licensePlateNumber);

            assertAll(
                    () -> assertNotNull(result),
                    () -> assertNotNull(result.getOut()),
                    () -> assertEquals(BigDecimal.ONE, result.getTotalAmount()));

            Mockito.verify(repository, Mockito.times(1)).findOneByLicensePlateNumberAndOutIsNull(licensePlateNumber);
            Mockito.verify(repository, Mockito.times(1)).saveAndFlush(parkingRegistry);
            Mockito.verifyNoMoreInteractions(repository);

            Mockito.verify(ratePolicyProvider, Mockito.times(1)).calculateTotal(parkingRegistry);
        }
    }
}