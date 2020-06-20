package com.parking.tollparkinglibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TestApplication extends SpringBootServletInitializer
{
    static
    {
        System.out.println("TestApplication.static initializer");

    }

    public static void main(String[] args)
    {
        SpringApplication.run(TestApplication.class, args);
    }
}
