package com.parking.tollparkinglibrary.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Base package to scan for components and repository configuration
 */
@SpringBootConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.parking.tollparkinglibrary"})
public class TollParkingLibraryApplicationConfiguration
{
}