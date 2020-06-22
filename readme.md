# Toll Parking Libray

REST API of a basic implementation of a toll parking management service

### Context

A toll parking lor provides several types of parking slots to its users. It has assigned slots
 for combustion engine vehicles as well as for electric ones. The latest are categorised depending on the 
 power supply their electric group is capable to deliver, being 20KW and 50KW.
 
 The entry/exit service must determine which slot type is assigned to each entering vehicle depending on
 its engine. This distinction can be done based on the vehicle ID (the license plate number). I no slots are available
 for the vehicle type, entrance to the parking must be refused.
 
 Each parking lot may apply a different parking rate policy to its clients. They may charge a fixed amount, a fixed 
 minute rate or any other policy, like a minute interval decremental rate.
 
 When leaving the lot, the client is issued a parking bill with the amount to pay before the toll opens.
 
 ### Prerequisites
 
 * JVM 11
 * JDK 11 & Maven when building from sources
 
 ### Build with
 
 * Java 11
 * Spring Boot 2
 * H2 Database
 
 ### Installation
 
 To install from sources:
 * Download or clone the repository
 
        git clone https://github.com/lusorio/toll-parking-library.git
    
 * Run it with Spring Boot
    
        mvn spring-boot:run
    
 #### Running in a different servlet container
 
 * Install the maven project
     
         mvn install
        
 * Deploy the generated .war artifact in the webapps (or equivalent) directory
         
 ### Test the application
 
 To run the test suite:
 
    mvn test
 
 ### Architecture
 
 #### Database and data access layer
 
 This projects uses an H2 in-memory DB to keep track of the parking slots' ([ParkingSlot.java](https://github.com/lusorio/toll-parking-library/blob/master/src/main/java/com/parking/tollparkinglibrary/models/ParkingSlot.java)) inventory as well
 as the ongoing and past transactions ([ParkingRegistry.java](https://github.com/lusorio/toll-parking-library/blob/master/src/main/java/com/parking/tollparkinglibrary/models/ParkingRegistry.java))
 
 Spring JPA / Hibernate is used in the data access layer, under the "repositories" folder.
 
 ### Configuration
 
 A configuration file [application.properties](https://github.com/lusorio/toll-parking-library/blob/master/src/main/resources/application.properties) is provided in the src/main/resources directory.
 
 #### Parking rate policy
 
 In order to configure the parking rate policy por a given parking lot, change the value of the property ``tpl.ratepolicy.type`` to the desired one. Current implementations are:
 * **Fixed amount**: where the parking clients are charged a fixed amount whatever the time they spend in the parking. If this rate policy applies, you need to configure the fixed rate in the property ``tpl.ratepolicy.fixed.fixedrate`
 * **Fixed minute rate**: where the parking clients are charged with a per-minute rate and, optionally, a fixed rate on arrival. If this rate applies, you need to configure the following properties: ``tpl.ratepolicy.fixedminute.fixedrate`` and ``tpl.ratepolicy.fixedminute.minuterate` 
 
 ** Note ** that all amounts must be strings representing floating point numbers i.e **2.0**, **0.05**, etc. 
 
 #### Implementing new rate policies
 
 New implementations of parking rate policies must implement the [https://github.com/lusorio/toll-parking-library/blob/master/src/main/java/com/parking/tollparkinglibrary/providers/parkingratepolicy/IParkingRatePolicyProvider.java] interface which ``calculateTotal(ParkingRegistry registry)`` method accepts
 a closed terminated [ParkingRegistry.java](https://github.com/lusorio/toll-parking-library/blob/master/src/main/java/com/parking/tollparkinglibrary/models/ParkingRegistry.java) instance, from which it can infer the time spent in the parking slot by the given vehicle.
 
 Active parking rate implementation is loaded dynamically by the [ParkingRateConfiguration.java](https://github.com/lusorio/toll-parking-library/blob/master/src/main/java/com/parking/tollparkinglibrary/configuration/ParkingRateConfiguration.java) configuration bean. This configuration must reflect new parking rate policie's implementations as well.

 ### Usage
 
 To simple methods are available in the API.
 
    /api/in
    
 and
    
    /api/out
    
 Both methods accept a single parameter which is a vehicle id (license plate number) which may contain any arbitrary string.
  Documentation on the API usage can be consulted in the Swagger documentation by accessing the **/swagger-ui.html** 
  from the application root. 
  
 [http://localhost:8080/swagger-ui.html]()
    
 A typical call to the API should be something similar to
 
 [http://localhost:8080/in?vehicleId=AAA-111-ZZ]()