package com.parking.tollparkinglibrary.repositories;

import com.parking.tollparkinglibrary.models.ParkingRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IParkingRegistryRepository extends JpaRepository<ParkingRegistry, Long>
{

    /**
     * Fetch the ongoing {@link ParkingRegistry} for a given vehicle.
     *
     * @param licensePlateNumber the id of the vehicle being search for
     * @return an {@link Optional} ongoing {@link ParkingRegistry}
     */
    Optional<ParkingRegistry> findOneByLicensePlateNumberAndOutIsNull(String licensePlateNumber);

}
