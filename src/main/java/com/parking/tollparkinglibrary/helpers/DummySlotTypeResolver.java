package com.parking.tollparkinglibrary.helpers;

import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements a dummy {@link ParkingSlotTypeEnum} resolver where the type is given randomly.
 */
@Component
public class DummySlotTypeResolver implements IParkingSlotTypeResolver
{
    @Override
    public ParkingSlotTypeEnum resolveParkingSlotType(String licensePlate)
    {
        if (StringUtils.isBlank(licensePlate))
        {
            return ParkingSlotTypeEnum.COMBUSTION;

        }

        return ParkingSlotTypeEnum.values()[ThreadLocalRandom.current().nextInt(ParkingSlotTypeEnum.values().length)];
    }
}
