package com.parking.tollparkinglibrary.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.parking.tollparkinglibrary.enumerations.ResponseStatusCodeEnum;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic response returned by all API methods.
 * <p>
 * Adding
 * - @JsonIgnore : to not return some fields
 * - @JsonInclude : to return only non null fields
 * http://tutorials.jenkov.com/java-json/jackson-annotations.html
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T>
{
    /**
     * Return status code
     */
    private ResponseStatusCodeEnum status;

    /**
     * The content of the response. Contains the result(s) of the business layer
     */
    private ResponseContent<T> content;

    /**
     * List of exceptions
     */
    private List<String> errors;

    /**
     * @return ResponseStatusCode
     */
    public ResponseStatusCodeEnum getStatus()
    {
        return status;
    }

    /**
     * @param status {@link ResponseStatusCodeEnum}
     */
    public Response<T> setStatus(ResponseStatusCodeEnum status)
    {
        this.status = status;
        return this;
    }

    /**
     * @return ResponseContent<T>
     */
    public ResponseContent<T> getContent()
    {
        return content;
    }

    /**
     * @param content ResponseContent
     */
    public void setContent(ResponseContent<T> content)
    {
        this.content = content;
    }

    /**
     * @return get the content's result
     */
    @JsonIgnore
    public T getResult()
    {
        if (content == null)
        {
            content = new ResponseContent<>();
        }

        return content.getResult();
    }

    /**
     * @return get the content's results list
     */
    @JsonIgnore
    public List<T> getResults()
    {
        if (content == null)
        {
            content = new ResponseContent<>();
        }

        return content.getResults();
    }

    /**
     * Add a result in the content
     */
    public void addResult(T obj)
    {
        if (content == null)
        {
            content = new ResponseContent<>();
        }

        content.addResult(obj);
    }

    /**
     * Add a list of results in the content
     */
    public void addResults(List<T> obj)
    {
        if (content == null)
        {
            content = new ResponseContent<>();
        }

        content.addResults(obj);
    }

    /**
     * @return the list of errors in the response
     */
    public List<String> getErrors()
    {
        return errors;
    }

    /**
     * Utility public method to know if the error list contains errors
     *
     * @return <code>true</code> if the list is not empty
     */
    public boolean hasErrors()
    {
        return errors != null && !errors.isEmpty();
    }

    /**
     * Add an error in the error list
     */
    public void addError(Exception exception)
    {
        if (errors == null)
        {
            errors = new ArrayList<>();
        }

        errors.add(exception.getMessage());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                       .append("status", status)
                       .append("content", getResult() != null ? getResult() : getResults())
                       .append("errors", errors)
                       .toString();
    }
}
