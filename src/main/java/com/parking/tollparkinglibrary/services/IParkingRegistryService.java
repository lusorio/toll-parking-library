package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.exceptions.BusinessException;
import com.parking.tollparkinglibrary.exceptions.NoSuchRegistryException;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.models.ParkingSlot;

public interface IParkingRegistryService
{
    /**
     * Initialise a registry
     *
     * @param licensePlateNumber the id of the vehicle
     * @param parkingSlot        the assigned {@link ParkingSlot}
     * @return a newly initialised {@link ParkingRegistry}
     * @throws BusinessException if a non categorised business exceptions occur
     */
    ParkingRegistry initParkingRegistry(String licensePlateNumber, ParkingSlot parkingSlot) throws BusinessException;

    /**
     * Closes an ongoing registry
     *
     * @param licensePlateNumber the id of the vehicle
     * @return the closed {@link ParkingRegistry}
     * @throws NoSuchRegistryException if no ongoing {@link ParkingRegistry} is found for the given vehicle
     */
    ParkingRegistry closeParkingRegistry(String licensePlateNumber) throws NoSuchRegistryException;
}
