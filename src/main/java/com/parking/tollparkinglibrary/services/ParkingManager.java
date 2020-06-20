package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.exceptions.BusinessException;
import com.parking.tollparkinglibrary.exceptions.CheckinException;
import com.parking.tollparkinglibrary.exceptions.CheckoutException;
import com.parking.tollparkinglibrary.helpers.IParkingSlotTypeResolver;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParkingManager implements IParkingManager
{
    @Autowired
    private IParkingSlotTypeResolver parkingSlotTypeResolver;

    @Autowired
    private IParkingSlotService parkingSlotService;

    @Autowired
    private IParkingRegistryService parkingRegistryService;

    @Override
    public synchronized ParkingRegistry checkIn(String licensePlateNumber) throws CheckinException
    {
        try
        {
            var selectedSlot = parkingSlotService.findAvailableParkingSlot(parkingSlotTypeResolver.resolveParkingSlotType(licensePlateNumber));

            // book the slot and initialize the operation
            parkingSlotService.bookParkingSlot(selectedSlot.getId());

            return parkingRegistryService.initParkingRegistry(licensePlateNumber, selectedSlot);
        }
        catch (BusinessException e)
        {
            throw new CheckinException(e.getMessage());
        }
    }

    @Override
    public synchronized ParkingRegistry checkOut(String licensePlateNumber) throws CheckoutException
    {
        try
        {
            var registry = parkingRegistryService.closeParkingRegistry(licensePlateNumber);
            parkingSlotService.releaseParkingSlot(registry.getSlot().getId());

            return registry;
        }
        catch (BusinessException e)
        {
            throw new CheckoutException(e.getMessage());
        }
    }
}