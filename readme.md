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
    
 * Install the maven project
    
        mvn install
    
 * Run it with Spring Boot
    
        mvn spring-boot:run
    
    
 ### Test the application
 
 To run the test suite:
 
    mvn test
 
 ### Architecture
 
 #### Database and data access layer
 
 This projects uses an H2 in-memory DB to keep track of the parking slots' ([ParkingSlot.java](https://github.com/lusorio/toll-parking-library/blob/master/src/main/java/com/parking/tollparkinglibrary/models/ParkingSlot.java)) inventory as well
 as the ongoing and past transactions ([ParkingRegistry.java](https://github.com/lusorio/toll-parking-library/blob/master/src/main/java/com/parking/tollparkinglibrary/models/ParkingRegistry.java))
 
 Spring JPA / Hibernate is used in the data access layer, under the "repositories" folder.
 
 ### Usage
 
 To simple methods are available in the API.
 
    /api/in
    
 and
    
    /api/out
    
 A typicall call to the API should be something similar to
 
 [http://localhost:8080/in?vehicleId=AAA-111-ZZ]()
 
 Both accept a single parameter which is a license plate number (vehicleId) which may contain an arbitrary string.
 Documentation on the API usage can be consulted in the Swagger documentation
 
 [http://localhost:8080/swagger-ui.html]()