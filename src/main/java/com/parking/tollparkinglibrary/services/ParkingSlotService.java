package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;
import com.parking.tollparkinglibrary.exceptions.NoParkingSlotAvailableException;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import com.parking.tollparkinglibrary.repositories.IParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParkingSlotService implements IParkingSlotService
{
    @Autowired
    private IParkingSlotRepository parkingSlotRepository;

    @Override
    @Transactional(readOnly = true)
    public synchronized ParkingSlot findAvailableParkingSlot(ParkingSlotTypeEnum type) throws NoParkingSlotAvailableException
    {
        var availableSlots = parkingSlotRepository.findAllByTypeAndAvailableTrue(type);

        if (availableSlots.isEmpty())
        {
            throw new NoParkingSlotAvailableException(NoParkingSlotAvailableException.MSG);
        }

        // any business logic on slot assignment such as getting the closest slot, or load balancing
        // to less busy alleys should be invoked here
        return availableSlots.get(0);
    }

    @Override
    public synchronized ParkingSlot bookParkingSlot(Long slotId)
    {
        var slot = parkingSlotRepository.findById(slotId).orElseThrow(IllegalArgumentException::new);
        slot.setAvailable(false);

        return parkingSlotRepository.saveAndFlush(slot);
    }

    @Override
    public synchronized ParkingSlot releaseParkingSlot(Long slotId)
    {
        var slot = parkingSlotRepository.findById(slotId).orElseThrow(IllegalArgumentException::new);
        slot.setAvailable(true);

        return parkingSlotRepository.saveAndFlush(slot);
    }
}