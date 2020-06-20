package com.parking.tollparkinglibrary.exceptions;

public class UnknownLicensePlateNumberException extends BusinessException
{
    public UnknownLicensePlateNumberException(String message)
    {
        super(message);
    }
}
