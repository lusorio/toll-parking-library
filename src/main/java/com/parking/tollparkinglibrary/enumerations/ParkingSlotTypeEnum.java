package com.parking.tollparkinglibrary.enumerations;

/**
 * Provides the list of parking slot types along with the electric power supply
 * available (if any) expressed in Kilowatts (KW)
 */
public enum ParkingSlotTypeEnum
{
    // standard slots, with no power supply
    COMBUSTION(0),

    // electric charge slot, restricted to electric engines with 20KW power supply
    ELECTRIC_20KW(20),

    // electric charge slot, restricted to electric engines with 50KW power supply
    ELECTRIC_50KW(50);

    // the electric power supply each slot type is able to deliver
    private int powerSupply;

    ParkingSlotTypeEnum(int powerSupply)
    {
        this.powerSupply = powerSupply;
    }

    public int getPowerSupply()
    {
        return powerSupply;
    }

    public boolean hasPowerSupply()
    {
        return this.powerSupply > 0;
    }
}
