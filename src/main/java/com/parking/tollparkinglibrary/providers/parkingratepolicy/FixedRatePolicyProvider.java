package com.parking.tollparkinglibrary.providers.parkingratepolicy;

import com.parking.tollparkinglibrary.models.ParkingRegistry;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Establishes the parking rate policy to a fixed rate mode.
 */
public class FixedRatePolicyProvider implements IParkingRatePolicyProvider
{
    @Value("${tpl.ratepolicy.fixed.fixedrate}")
    private String fixedRate;

    public FixedRatePolicyProvider()
    {
        // empty constructor
    }

    public FixedRatePolicyProvider(String fixedRate)
    {
        this.fixedRate = fixedRate;
    }

    public String getFixedRate()
    {
        return fixedRate;
    }

    public FixedRatePolicyProvider setFixedRate(String fixedRate)
    {
        this.fixedRate = fixedRate;
        return this;
    }

    @Override
    public BigDecimal calculateTotal(ParkingRegistry registry)
    {
        if (registry.getIn() == null || registry.getOut() == null)
        {
            throw new IllegalArgumentException("Parking registry must be closed");
        }

        return new BigDecimal(fixedRate).setScale(2, RoundingMode.HALF_EVEN);
    }
}