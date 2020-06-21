package com.parking.tollparkinglibrary.controllers;

import com.parking.tollparkinglibrary.enumerations.ResponseStatusCodeEnum;
import com.parking.tollparkinglibrary.exceptions.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Base abstract class for API controllers
 * <p>
 * Provides common methods to handle API responses, adding content and errors on them
 */
@RestController
@RequestMapping("/api")
public abstract class AbstractController
{
    /**
     * Builds a simple Response containing a single object in the content
     *
     * @param content the response's content
     * @param <T>     the type of the content
     * @return a {@link Response} object
     */
    protected <T> Response<T> getResponse(T content)
    {
        return getGenericResponse(content);
    }

    /**
     * Builds a simple Response containing a list of objects in the content
     *
     * @param content the response's content
     * @param <T>     the type of the content
     * @return a {@link Response} object
     */
    protected <T> Response<T> getResponse(List<T> content)
    {
        return getGenericResponse(content);
    }

    /**
     * Builds a generic {@link ResponseStatusCodeEnum#FAILURE} response with a single error
     *
     * @param exception the exception for the response
     * @param <T>       the expected type of the content
     * @return a {@link Response} object
     */
    protected <T> Response<T> getResponseError(BusinessException exception)
    {
        var response = new Response<T>();
        response.setStatus(ResponseStatusCodeEnum.FAILURE);
        response.addError(exception);
        return response;
    }

    /**
     * Builds a generic {@link ResponseStatusCodeEnum#FAILURE} response with a list of errors
     *
     * @param exceptions the list of exceptions for the response
     * @param <T>        the expected type of the content
     * @return a {@link Response} object
     */
    protected <T> Response<T> getResponseError(List<BusinessException> exceptions)
    {
        var response = new Response<T>();
        response.setStatus(ResponseStatusCodeEnum.FAILURE);
        exceptions.forEach(response::addError);
        return response;
    }

    /**
     * Builds a generic {@link ResponseStatusCodeEnum#SUCCESS} response with a single object
     * in the content
     *
     * @param content the response's content
     * @param <T>     the type of the content
     * @return a {@link Response} object
     */
    private <T> Response<T> getGenericResponse(T content)
    {
        var response = new Response<T>();

        response.setStatus(ResponseStatusCodeEnum.SUCCESS);
        response.addResult(content);

        return response;
    }

    /**
     * Builds a generic {@link ResponseStatusCodeEnum#SUCCESS} response with a list of objects
     * in the content
     *
     * @param content the response's content
     * @param <T>     the type of the content
     * @return a {@link Response} object
     */
    private <T> Response<T> getGenericResponse(List<T> content)
    {
        var response = new Response<T>();

        response.setStatus(ResponseStatusCodeEnum.SUCCESS);
        response.addResults(content);

        return response;
    }
}
