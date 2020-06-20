package com.parking.tollparkinglibrary.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Representation of the possible states of an API response
 */
public enum ResponseStatusCodeEnum
{
    SUCCESS(0), FAILURE(1);

    /**
     * Status code
     */
    private int status;

    ResponseStatusCodeEnum(int status)
    {
        this.status = status;
    }

    @JsonValue
    public int get()
    {
        return status;
    }

    public boolean isSuccess()
    {
        return SUCCESS.get() == get();
    }

    public boolean isFailure()
    {
        return FAILURE.get() == get();
    }
}