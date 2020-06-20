package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.exceptions.CheckinException;
import com.parking.tollparkinglibrary.exceptions.CheckoutException;
import com.parking.tollparkinglibrary.models.ParkingRegistry;

public interface IParkingManager
{
    /**
     * Handles input vehicles with {@link com.parking.tollparkinglibrary.models.ParkingSlot} assignation and slot stock management.
     *
     * @param licensePlateNumber the id of the input vehicle
     * @return the user-friendly code of the {@link com.parking.tollparkinglibrary.models.ParkingSlot}
     * @throws CheckinException if any exception is raised that makes the checking impossible
     */
    ParkingRegistry checkIn(String licensePlateNumber) throws CheckinException;

    /**
     * Handles output vehicles.
     *
     * @param licensePlateNumber the id of the output vehicle
     * @return the {@link ParkingRegistry} for the vehicle
     * @throws CheckoutException if any exception is raised that makes the checkout impossible
     */
    ParkingRegistry checkOut(String licensePlateNumber) throws CheckoutException;
}
