package com.parking.tollparkinglibrary.providers.parkingratepolicy;

import com.parking.tollparkinglibrary.models.ParkingRegistry;

import java.math.BigDecimal;

public interface IParkingRatePolicyProvider
{
    BigDecimal calculateTotal(ParkingRegistry registry);
}
