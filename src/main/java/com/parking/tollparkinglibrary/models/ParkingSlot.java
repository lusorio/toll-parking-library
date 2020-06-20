package com.parking.tollparkinglibrary.models;

import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;

import javax.persistence.*;

/**
 * Represents a slot in a given parking lot. Parking slots have an unique id and a user-friendly code such as C-31.
 * Slots are also typed as for whether or not they're equipped with an electric power supply and, for the latest, which
 * the actual charge delivery.
 * <p>
 * Parking slots may or may not be available at any given moment.
 */
@Entity
@Table(name = "parking_slot")
public class ParkingSlot
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParkingSlotTypeEnum type;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "available", nullable = false)
    private boolean available = true;

    public ParkingSlot()
    {
        // empty constructor
    }

    public ParkingSlot(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public ParkingSlot setId(Long id)
    {
        this.id = id;
        return this;
    }

    public ParkingSlotTypeEnum getType()
    {
        return type;
    }

    public ParkingSlot setType(ParkingSlotTypeEnum type)
    {
        this.type = type;
        return this;
    }

    public String getCode()
    {
        return code;
    }

    public ParkingSlot setCode(String code)
    {
        this.code = code;
        return this;
    }

    public boolean isAvailable()
    {
        return available;
    }

    public ParkingSlot setAvailable(boolean available)
    {
        this.available = available;
        return this;
    }
}
