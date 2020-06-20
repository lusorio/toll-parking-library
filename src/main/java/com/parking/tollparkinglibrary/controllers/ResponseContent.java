package com.parking.tollparkinglibrary.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic response content returned by all API methods.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseContent<T>
{
    /**
     * A single result object
     */
    private T result;

    /**
     * List of result objects
     */
    private List<T> results;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T getResult()
    {
        return result;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<T> getResults()
    {
        return results;
    }

    /**
     * Add a result to the response
     */
    public void addResult(T obj)
    {
        result = obj;

        // clear results - both cannot coexist
        if (results != null)
        {
            results = null;
        }
    }

    /**
     * Add a list of results in the response
     */
    public void addResults(List<T> obj)
    {
        if (obj == null)
        {
            throw new RuntimeException("object cannot be null");
        }
        if (results == null)
        {
            results = new ArrayList<>();
        }

        results.addAll(obj);

        // clear result - both cannot coexist
        result = null;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                       .append("result", result)
                       .append("results", results != null ? results.size() : null)
                       .toString();
    }
}

