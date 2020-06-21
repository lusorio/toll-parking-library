package com.parking.tollparkinglibrary.services;

import com.parking.tollparkinglibrary.exceptions.BusinessException;
import com.parking.tollparkinglibrary.exceptions.NoSuchRegistryException;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import com.parking.tollparkinglibrary.providers.parkingratepolicy.IParkingRatePolicyProvider;
import com.parking.tollparkinglibrary.repositories.IParkingRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class ParkingRegistryService implements IParkingRegistryService
{
    @Autowired
    private IParkingRegistryRepository parkingRegistryRepository;

    @Autowired
    private IParkingRatePolicyProvider parkingRatePolicyProvider;

    @Override
    public ParkingRegistry initParkingRegistry(String licensePlateNumber, ParkingSlot parkingSlot) throws BusinessException
    {
        if (parkingRegistryRepository.findOneByLicensePlateNumberAndOutIsNull(licensePlateNumber).isPresent())
        {
            throw new BusinessException("Ongoing registry already found for this vehicle id");
        }

        return parkingRegistryRepository.saveAndFlush(new ParkingRegistry(licensePlateNumber, parkingSlot));
    }

    @Override
    public ParkingRegistry closeParkingRegistry(String licensePlateNumber) throws NoSuchRegistryException
    {

        var registry = parkingRegistryRepository.findOneByLicensePlateNumberAndOutIsNull(licensePlateNumber)
                                                .orElseThrow(() -> new NoSuchRegistryException(NoSuchRegistryException.MSG));

        registry.setOut(LocalDateTime.now());
        registry.setTotalAmount(parkingRatePolicyProvider.calculateTotal(registry));
        parkingRegistryRepository.saveAndFlush(registry);

        return registry;
    }
}
