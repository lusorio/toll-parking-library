package com.parking.tollparkinglibrary.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representation of a parking occupancy. A registry is the usage of a given parking slot by a given vehicle at a
 * precise time interval.
 * <p>
 * A registry is initialised once a car is assigned a parking slot on arrival, and completed once the vehicle exits the parking. Billing
 * is issued when a given registry gets closed.
 */
@Entity
@Table(name = "parking_registry")
public class ParkingRegistry
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "license_plate_number", nullable = false)
    private String licensePlateNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "slot_id")
    @Fetch(FetchMode.JOIN)
    private ParkingSlot slot;

    @Column(name = "datetime_in", nullable = false)
    private LocalDateTime in;

    @Column(name = "datetime_out")
    private LocalDateTime out;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    public ParkingRegistry()
    {
        // empty constructor
    }

    public ParkingRegistry(String licensePlateNumber, ParkingSlot slot)
    {
        this.licensePlateNumber = licensePlateNumber;
        this.slot = slot;
        this.in = LocalDateTime.now();
    }

    public Long getId()
    {
        return id;
    }

    public ParkingRegistry setId(Long id)
    {
        this.id = id;
        return this;
    }

    public String getLicensePlateNumber()
    {
        return licensePlateNumber;
    }

    public ParkingRegistry setLicensePlateNumber(String licensePlate)
    {
        this.licensePlateNumber = licensePlate;
        return this;
    }

    public ParkingSlot getSlot()
    {
        return slot;
    }

    public ParkingRegistry setSlot(ParkingSlot slot)
    {
        this.slot = slot;
        return this;
    }

    public LocalDateTime getIn()
    {
        return in;
    }

    public ParkingRegistry setIn(LocalDateTime in)
    {
        this.in = in;
        return this;
    }

    public LocalDateTime getOut()
    {
        return out;
    }

    public ParkingRegistry setOut(LocalDateTime out)
    {
        this.out = out;
        return this;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public ParkingRegistry setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
        return this;
    }
}
