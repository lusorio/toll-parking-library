package com.parking.tollparkinglibrary.helpers;

import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;

/**
 * This interface provide a way to resolve the {@link ParkingSlotTypeEnum} to be assigned to a vehicle on arrival.
 */
public interface IParkingSlotTypeResolver
{
    /**
     * Determine the {@link ParkingSlotTypeEnum} to be assigned to a vehicle based on its license plate
     *
     * @param licensePlateNumber the vehicle's license plate number
     * @return the {@link ParkingSlotTypeEnum} the vehicle fits in based on its engine type. If a given plate is not recognised
     * a standard place will be assigned to the vehicle
     */
    ParkingSlotTypeEnum resolveParkingSlotType(String licensePlateNumber);
}
