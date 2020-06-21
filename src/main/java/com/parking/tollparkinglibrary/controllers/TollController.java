package com.parking.tollparkinglibrary.controllers;

import com.parking.tollparkinglibrary.exceptions.CheckinException;
import com.parking.tollparkinglibrary.exceptions.CheckoutException;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.services.IParkingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TollController extends AbstractController
{
    @Autowired
    private IParkingManager parkingManager;

    @GetMapping("/in")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Response<ParkingRegistry> enterParking(@RequestParam("vehicleId") String vehicleId)
    {
        try
        {
            return getResponse(parkingManager.checkIn(vehicleId));
        }
        catch (CheckinException e)
        {
            return getResponseError(e);
        }
    }

    @GetMapping("/out")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Response<ParkingRegistry> exitParking(@RequestParam("vehicleId") String vehicleId)
    {
        try
        {
            return getResponse(parkingManager.checkOut(vehicleId));
        }
        catch (CheckoutException e)
        {
            return getResponseError(e);
        }
    }
}
