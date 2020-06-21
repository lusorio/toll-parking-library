package com.parking.tollparkinglibrary.exceptions;

public class NoSuchRegistryException extends BusinessException
{
    public static final String MSG = "No ongoing registry for this vehicle";

    public NoSuchRegistryException(String message)
    {
        super(message);
    }
}
