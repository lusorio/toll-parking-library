package com.parking.tollparkinglibrary.controllers;

import com.parking.tollparkinglibrary.enumerations.ResponseStatusCodeEnum;
import com.parking.tollparkinglibrary.exceptions.BusinessException;
import com.parking.tollparkinglibrary.exceptions.CheckinException;
import com.parking.tollparkinglibrary.exceptions.CheckoutException;
import com.parking.tollparkinglibrary.models.ParkingRegistry;
import com.parking.tollparkinglibrary.services.IParkingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TollController
{
    @Autowired
    private IParkingManager parkingManager;

    @GetMapping("/in/{vehicleId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Response<ParkingRegistry> enterParking(@PathVariable("vehicleId") String vehicleId)
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

    @GetMapping("/out/{vehicleId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    public Response<ParkingRegistry> exitParking(@PathVariable("vehicleId") String vehicleId)
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

    /**
     * When there is a business validation, like configuration parameter missing
     * It returns a HTTP 200 error code
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> requestBusinessExceptionHandler(BusinessException e)
    {
        return getResponseError(List.of(e));
    }

    /**
     * Simple Response containing a single element
     *
     * @param content
     * @param <T>
     * @return
     */
    protected <T> Response<T> getResponse(T content)
    {
        return getGenericResponse(content);
    }

    /**
     * Simple Response containing a list
     *
     * @param content
     * @param <T>
     * @return
     */
    protected <T> Response<T> getResponse(List<T> content)
    {
        return getGenericResponse(content);
    }

    /**
     * Generic response for one item
     *
     * @param content
     * @param <T>
     * @return
     */
    private <T> Response<T> getGenericResponse(T content)
    {
        var response = new Response<T>();

        response.setStatus(ResponseStatusCodeEnum.SUCCESS);
        response.addResult(content);

        return response;
    }

    /**
     * Generic response for a list of items
     *
     * @param content
     * @param <T>
     * @return
     */
    private <T> Response<T> getGenericResponse(List<T> content)
    {
        var response = new Response<T>();

        response.setStatus(ResponseStatusCodeEnum.SUCCESS);
        response.addResults(content);

        return response;
    }

    /**
     * Create a generic Response from the list of exception
     *
     * @param exception
     * @return
     */
    private <T> Response<T> getResponseError(BusinessException exception)
    {
        var response = new Response<T>();
        response.setStatus(ResponseStatusCodeEnum.FAILURE);
        response.addError(exception);
        return response;
    }

    /**
     * Create a generic Response from the list of exception
     *
     * @param exception
     * @return
     */
    private <T> Response<T> getResponseError(List<BusinessException> exception)
    {
        var response = new Response<T>();
        response.setStatus(ResponseStatusCodeEnum.FAILURE);
        exception.forEach(response::addError);
        return response;
    }
}
