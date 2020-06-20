package com.parking.tollparkinglibrary.providers.parkingratepolicy;

import com.parking.tollparkinglibrary.models.ParkingRegistry;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

/**
 * Establishes the parking rate policy to a fixed minute rate mode.
 * <p>
 * Optionally, a fixed amount is charged. Then, each minute is charged with a predefined fixed amount.
 * - Fixed rate  = 1.0
 * - Minute rate = 0.05
 */
public class FixedMinuteRatePolicyProvider implements IParkingRatePolicyProvider
{
    @Value("${tpl.ratepolicy.fixedminute.fixedrate}")
    private String fixedRate;

    @Value("${tpl.ratepolicy.fixedminute.minuterate}")
    private String minuteRate;

    public FixedMinuteRatePolicyProvider()
    {
        // empty constructor
    }

    public FixedMinuteRatePolicyProvider(String fixedRate, String minuteRate)
    {
        this.fixedRate = fixedRate;
        this.minuteRate = minuteRate;
    }

    public String getFixedRate()
    {
        return fixedRate;
    }

    public FixedMinuteRatePolicyProvider setFixedRate(String fixedRate)
    {
        this.fixedRate = fixedRate;
        return this;
    }

    public String getMinuteRate()
    {
        return minuteRate;
    }

    public FixedMinuteRatePolicyProvider setMinuteRate(String minuteRate)
    {
        this.minuteRate = minuteRate;
        return this;
    }

    @Override
    public BigDecimal calculateTotal(ParkingRegistry registry)
    {
        if (registry.getIn() == null || registry.getOut() == null)
        {
            throw new IllegalArgumentException("Parking registry must be closed");
        }

        var durationInMinutes = Duration.between(registry.getIn(), registry.getOut()).toMinutes();
        var total = new BigDecimal(minuteRate).multiply(new BigDecimal(durationInMinutes)).add(new BigDecimal(fixedRate));

        return total.setScale(2, RoundingMode.HALF_EVEN);
    }
}
