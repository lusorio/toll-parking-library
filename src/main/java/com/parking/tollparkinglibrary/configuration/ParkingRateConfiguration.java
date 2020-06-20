package com.parking.tollparkinglibrary.configuration;

import com.parking.tollparkinglibrary.providers.parkingratepolicy.FixedMinuteRatePolicyProvider;
import com.parking.tollparkinglibrary.providers.parkingratepolicy.FixedRatePolicyProvider;
import com.parking.tollparkinglibrary.providers.parkingratepolicy.IParkingRatePolicyProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Conditionally creates the parking rate policy beans based on the application configuration
 */
@Configuration
public class ParkingRateConfiguration
{
    @Bean
    @ConditionalOnProperty(
            value = "tpl.ratepolicy.type",
            havingValue = "fixed")
    public IParkingRatePolicyProvider getFixedRatePolicy()
    {
        return new FixedRatePolicyProvider();
    }

    @Bean
    @ConditionalOnProperty(
            value = "tpl.ratepolicy.type",
            havingValue = "fixedminute")
    public IParkingRatePolicyProvider getFixedMinuteRatePolicy()
    {
        return new FixedMinuteRatePolicyProvider();
    }
}
