package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.AbstractTest;
import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;
import com.parking.tollparkinglibrary.exceptions.NoParkingSlotAvailableException;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import com.parking.tollparkinglibrary.repositories.IParkingSlotRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

class ParkingSlotServiceTest extends AbstractTest
{
    @Mock
    private IParkingSlotRepository repository;

    @InjectMocks
    private ParkingSlotService service;

    @Nested
    @DisplayName("Test the findAvailableParkingSlot method")
    class FindAvailableParkingSlot
    {
        @ParameterizedTest
        @EnumSource(ParkingSlotTypeEnum.class)
        @DisplayName("Assert that if no slots are available for a given type, a NoParkingSlotAvailableException is raised")
        void findAvailableParkingSlot_Error_NoSlotAvailable(ParkingSlotTypeEnum type)
        {
            Mockito.when(repository.findAllByTypeAndAvailableTrue(any())).thenReturn(List.of());

            assertThrows(NoParkingSlotAvailableException.class, () -> service.findAvailableParkingSlot(type));

            Mockito.verify(repository, Mockito.times(1)).findAllByTypeAndAvailableTrue(type);
            Mockito.verifyNoMoreInteractions(repository);
        }

        @ParameterizedTest
        @EnumSource(ParkingSlotTypeEnum.class)
        @DisplayName("Assert that the first available slot is returned")
        void findAvailableParkingSlot_OK(ParkingSlotTypeEnum type) throws NoParkingSlotAvailableException
        {
            Mockito.when(repository.findAllByTypeAndAvailableTrue(any()))
                   .thenReturn(List.of(new ParkingSlot(1L),
                                       new ParkingSlot(2L),
                                       new ParkingSlot(3L)));

            assertEquals(1L, service.findAvailableParkingSlot(type).getId());

            Mockito.verify(repository, Mockito.times(1)).findAllByTypeAndAvailableTrue(type);
            Mockito.verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    @DisplayName("Test the bookParkingSlot method")
    class BookParkingSlot
    {
        @ParameterizedTest
        @ValueSource(longs = 1L)
        @NullSource
        @DisplayName("Assert that an IllegalArgumentException is thrown if a bad slotId is passed to the method")
        void bookParkingSlot_Error_SlotNotFound(Long slotId)
        {
            Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, () -> service.bookParkingSlot(slotId));

            Mockito.verify(repository, Mockito.times(1)).findById(slotId);
            Mockito.verifyNoMoreInteractions(repository);
        }

        @ParameterizedTest
        @ValueSource(longs = {1L, 10L, 100L})
        @DisplayName("Assert that the booked slot is returned and that is no longer available")
        void bookParkingSlot(Long slotId)
        {
            Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(new ParkingSlot(slotId)));
            Mockito.when(repository.saveAndFlush(any(ParkingSlot.class))).then(AdditionalAnswers.returnsFirstArg());

            var bookedSlot = service.bookParkingSlot(slotId);

            assertAll(
                    () -> assertNotNull(bookedSlot),
                    () -> assertFalse(bookedSlot.isAvailable()));

            // verify dependencies interactions
            Mockito.verify(repository, Mockito.times(1)).findById(slotId);
            Mockito.verify(repository, Mockito.times(1)).saveAndFlush(bookedSlot);
            Mockito.verifyNoMoreInteractions(repository);
        }
    }

    @Nested
    @DisplayName("Test the freeParkingSlot method")
    class FreeParkingSlot
    {
        @ParameterizedTest
        @ValueSource(longs = 1L)
        @NullSource
        @DisplayName("Assert that an IllegalArgumentException is thrown if a bad slotId is passed to the method")
        void bookParkingSlot_Error_SlotNotFound(Long slotId)
        {
            Mockito.when(repository.findById(anyLong())).thenReturn(Optional.empty());

            assertThrows(IllegalArgumentException.class, () -> service.bookParkingSlot(slotId));

            Mockito.verify(repository, Mockito.times(1)).findById(slotId);
            Mockito.verifyNoMoreInteractions(repository);
        }

        @ParameterizedTest
        @ValueSource(longs = {1L, 10L, 100L})
        @DisplayName("Assert that the freed slot is returned and that available again")
        void bookParkingSlot(Long slotId)
        {
            Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(new ParkingSlot(slotId)));
            Mockito.when(repository.saveAndFlush(any(ParkingSlot.class))).then(AdditionalAnswers.returnsFirstArg());

            var bookedSlot = service.releaseParkingSlot(slotId);

            assertAll(
                    () -> assertNotNull(bookedSlot),
                    () -> assertTrue(bookedSlot.isAvailable()));

            // verify dependencies interactions
            Mockito.verify(repository, Mockito.times(1)).findById(slotId);
            Mockito.verify(repository, Mockito.times(1)).saveAndFlush(bookedSlot);
            Mockito.verifyNoMoreInteractions(repository);
        }
    }
}