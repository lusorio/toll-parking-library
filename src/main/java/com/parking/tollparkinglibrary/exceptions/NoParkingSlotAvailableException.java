package com.parking.tollparkinglibrary.exceptions;

public class NoParkingSlotAvailableException extends BusinessException
{
    public static final String MSG = "No suitable parking slot available for this vehicle type";

    public NoParkingSlotAvailableException(String message)
    {
        super(message);
    }
}
