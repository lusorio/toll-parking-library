package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;
import com.parking.tollparkinglibrary.exceptions.NoParkingSlotAvailableException;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import org.springframework.stereotype.Service;

@Service
public interface IParkingSlotService
{
    /**
     * Finds an available slot of a given {@link ParkingSlotTypeEnum} by applying a logic on the research approach
     *
     * @param type the needed {@link ParkingSlotTypeEnum}
     * @return an arbitrary {@link ParkingSlot}
     * @throws NoParkingSlotAvailableException if there are no suitable parking slots available
     */
    ParkingSlot findAvailableParkingSlot(ParkingSlotTypeEnum type) throws NoParkingSlotAvailableException;

    /**
     * Book a {@link ParkingSlot}
     *
     * @param slotId the slot id
     * @return the booked {@link ParkingSlot}
     * @throws IllegalArgumentException if the slotId is not found
     */
    ParkingSlot bookParkingSlot(Long slotId);

    /**
     * Free a {@link ParkingSlot}
     *
     * @param slotId the slot id
     * @return the freed {@link ParkingSlot}
     * @throws IllegalArgumentException if the slotId is not found
     */
    ParkingSlot releaseParkingSlot(Long slotId);
}
