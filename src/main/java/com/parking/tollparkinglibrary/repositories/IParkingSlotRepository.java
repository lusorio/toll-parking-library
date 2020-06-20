package com.parking.tollparkinglibrary.repositories;

import com.parking.tollparkinglibrary.enumerations.ParkingSlotTypeEnum;
import com.parking.tollparkinglibrary.models.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IParkingSlotRepository extends JpaRepository<ParkingSlot, Long>
{
    /**
     * Fetch a list of available parking slots for a given {@link ParkingSlotTypeEnum}. A list is returned instead
     * of a single slot so business logic may be more easily implemented over the resulting collection (i.e sorting
     * slots and returning the closest one)
     *
     * @param type the expected {@link ParkingSlotTypeEnum}
     * @return a {@link java.util.List} of available {@link ParkingSlot} of the given {@link ParkingSlotTypeEnum}
     */
    List<ParkingSlot> findAllByTypeAndAvailableTrue(ParkingSlotTypeEnum type);
}
