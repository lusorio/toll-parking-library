package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;
import com.parking.tollparkinglibrary.exceptions.*;
import com.parking.tollparkinglibrary.helpers.DummySlotTypeResolver;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

class ParkingManagerTest extends AbstractTest
{

    @Mock
    private DummySlotTypeResolver parkingSlotTypeResolver;

    @Mock
    private ParkingSlotService parkingSlotService;

    @Mock
    private ParkingRegistryService parkingRegistryService;

    @InjectMocks
    private ParkingManager manager;

    @Nested
    class Checkin
    {
        @Test
        void checkIn_Error_NoAvailableSlot() throws NoParkingSlotAvailableException
        {
            var vehicleId = "ABC";
            Mockito.when(parkingSlotTypeResolver.resolveParkingSlotType(anyString())).thenReturn(ParkingSlotTypeEnum.COMBUSTION);
            Mockito.when(parkingSlotService.findAvailableParkingSlot(any(ParkingSlotTypeEnum.class))).thenThrow(NoParkingSlotAvailableException.class);

            assertThrows(CheckinException.class, () -> manager.checkIn(vehicleId));

            Mockito.verify(parkingSlotTypeResolver, Mockito.times(1)).resolveParkingSlotType(vehicleId);
            Mockito.verifyNoMoreInteractions(parkingSlotTypeResolver);

            Mockito.verify(parkingSlotService, Mockito.times(1)).findAvailableParkingSlot(ParkingSlotTypeEnum.COMBUSTION);
            Mockito.verifyNoMoreInteractions(parkingSlotService);

            Mockito.verifyNoInteractions(parkingRegistryService);
        }

        @Test
        void checkIn_Error_IllegalArgumentException() throws NoParkingSlotAvailableException
        {
            var vehicleId = "ABC";
            var selectedSlot = new ParkingSlot(1L);

            Mockito.when(parkingSlotTypeResolver.resolveParkingSlotType(anyString())).thenReturn(ParkingSlotTypeEnum.COMBUSTION);
            Mockito.when(parkingSlotService.findAvailableParkingSlot(any(ParkingSlotTypeEnum.class))).thenReturn(selectedSlot);
            Mockito.when(parkingSlotService.bookParkingSlot(any())).thenThrow(IllegalArgumentException.class);

            assertThrows(IllegalArgumentException.class, () -> manager.checkIn(vehicleId));

            Mockito.verify(parkingSlotTypeResolver, Mockito.times(1)).resolveParkingSlotType(vehicleId);
            Mockito.verifyNoMoreInteractions(parkingSlotTypeResolver);

            Mockito.verify(parkingSlotService, Mockito.times(1)).findAvailableParkingSlot(ParkingSlotTypeEnum.COMBUSTION);
            Mockito.verify(parkingSlotService, Mockito.times(1)).bookParkingSlot(selectedSlot.getId());
            Mockito.verifyNoMoreInteractions(parkingSlotService);

            Mockito.verifyNoInteractions(parkingRegistryService);
        }

        @Test
        void checkIn_Error_GenericBusinessException() throws NoParkingSlotAvailableException, BusinessException
        {
            var vehicleId = "ABC";
            var selectedSlot = new ParkingSlot(1L);

            Mockito.when(parkingSlotTypeResolver.resolveParkingSlotType(anyString())).thenReturn(ParkingSlotTypeEnum.COMBUSTION);
            Mockito.when(parkingSlotService.findAvailableParkingSlot(any(ParkingSlotTypeEnum.class))).thenReturn(selectedSlot);
            Mockito.when(parkingSlotService.bookParkingSlot(any())).thenReturn(selectedSlot);
            Mockito.when(parkingRegistryService.initParkingRegistry(anyString(), any(ParkingSlot.class))).thenThrow(BusinessException.class);

            assertThrows(CheckinException.class, () -> manager.checkIn(vehicleId));

            Mockito.verify(parkingSlotTypeResolver, Mockito.times(1)).resolveParkingSlotType(vehicleId);
            Mockito.verifyNoMoreInteractions(parkingSlotTypeResolver);

            Mockito.verify(parkingSlotService, Mockito.times(1)).findAvailableParkingSlot(ParkingSlotTypeEnum.COMBUSTION);
            Mockito.verify(parkingSlotService, Mockito.times(1)).bookParkingSlot(selectedSlot.getId());
            Mockito.verifyNoMoreInteractions(parkingSlotService);

            Mockito.verify(parkingRegistryService, Mockito.times(1)).initParkingRegistry(vehicleId, selectedSlot);
            Mockito.verifyNoMoreInteractions(parkingRegistryService);
        }

        @Test
        void checkIn_OK() throws NoParkingSlotAvailableException, BusinessException
        {
            var vehicleId = "ABC";
            var selectedSlot = new ParkingSlot(1L);
            var registry = new ParkingRegistry(vehicleId, selectedSlot);

            Mockito.when(parkingSlotTypeResolver.resolveParkingSlotType(anyString())).thenReturn(ParkingSlotTypeEnum.COMBUSTION);
            Mockito.when(parkingSlotService.findAvailableParkingSlot(any(ParkingSlotTypeEnum.class))).thenReturn(selectedSlot);
            Mockito.when(parkingSlotService.bookParkingSlot(any())).thenReturn(selectedSlot);
            Mockito.when(parkingRegistryService.initParkingRegistry(anyString(), any(ParkingSlot.class))).thenReturn(registry);

            var result = manager.checkIn(vehicleId);

            Mockito.verify(parkingSlotTypeResolver, Mockito.times(1)).resolveParkingSlotType(vehicleId);
            Mockito.verifyNoMoreInteractions(parkingSlotTypeResolver);

            Mockito.verify(parkingSlotService, Mockito.times(1)).findAvailableParkingSlot(ParkingSlotTypeEnum.COMBUSTION);
            Mockito.verify(parkingSlotService, Mockito.times(1)).bookParkingSlot(selectedSlot.getId());
            Mockito.verifyNoMoreInteractions(parkingSlotService);

            Mockito.verify(parkingRegistryService, Mockito.times(1)).initParkingRegistry(vehicleId, selectedSlot);
            Mockito.verifyNoMoreInteractions(parkingRegistryService);
        }
    }

    @Nested
    class Checkout
    {
        @Test
        void checkOut_Error_BusinessException() throws BusinessException
        {
            var vehicleId = "ABC";
            Mockito.when(parkingRegistryService.closeParkingRegistry(anyString())).thenThrow(NoSuchRegistryException.class);

            assertThrows(CheckoutException.class, () -> manager.checkOut(vehicleId));

            Mockito.verifyNoInteractions(parkingSlotTypeResolver);

            Mockito.verifyNoInteractions(parkingSlotService);

            Mockito.verify(parkingRegistryService, Mockito.times(1)).closeParkingRegistry(vehicleId);
            Mockito.verifyNoMoreInteractions(parkingRegistryService);
        }

        @Test
        void checkOut_Error_IllegalArgumentException() throws BusinessException
        {
            var vehicleId = "ABC";
            var selectedSlot = new ParkingSlot(1L);
            var registry = new ParkingRegistry(vehicleId, selectedSlot);

            Mockito.when(parkingRegistryService.closeParkingRegistry(anyString())).thenReturn(registry);
            Mockito.when(parkingSlotService.releaseParkingSlot(anyLong())).thenThrow(IllegalArgumentException.class);

            assertThrows(IllegalArgumentException.class, () -> manager.checkOut(vehicleId));

            Mockito.verifyNoInteractions(parkingSlotTypeResolver);

            Mockito.verify(parkingRegistryService, Mockito.times(1)).closeParkingRegistry(vehicleId);
            Mockito.verifyNoMoreInteractions(parkingRegistryService);

            Mockito.verify(parkingSlotService, Mockito.times(1)).releaseParkingSlot(registry.getSlot().getId());
            Mockito.verifyNoMoreInteractions(parkingSlotService);
        }

        @Test
        void checkOut_OK() throws BusinessException
        {
            var vehicleId = "ABC";
            var selectedSlot = new ParkingSlot(1L);
            var registry = new ParkingRegistry(vehicleId, selectedSlot);

            Mockito.when(parkingRegistryService.closeParkingRegistry(anyString())).thenReturn(registry);

            manager.checkOut(vehicleId);

            Mockito.verifyNoInteractions(parkingSlotTypeResolver);

            Mockito.verify(parkingRegistryService, Mockito.times(1)).closeParkingRegistry(vehicleId);
            Mockito.verifyNoMoreInteractions(parkingRegistryService);

            Mockito.verify(parkingSlotService, Mockito.times(1)).releaseParkingSlot(registry.getSlot().getId());
            Mockito.verifyNoMoreInteractions(parkingSlotService);
        }
    }
}