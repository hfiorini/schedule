# Simple Scheduling System

Requirements:
- Java 17
- Maven 3.9.6
- Docker 26.1.1

To run this app:
- Run `mvn clean install`
- Then `docker-compose build && docker-compose up`
- After app initialization, you'll be able to use Swagger at this URL:
`http://localhost:9000/swagger-ui/index.html#/`

A script with some initial records is included in  resources/data.sql file

# UML Diagram

![uml.png](src%2Fmain%2Fresources%2Fuml.png)

# Design

Doctors availability was designed creating a list of WorkingHours for each doctor.
Each WorkingHour has a doctor_id, dayOfTheWeek and startTime and endTime, so this way, doctors can create a set of availability slots for a given day.

When an appointment is created, a doctor_id, startDateTime and a endDateTime is requested and saved in the DB

To show the availability, the appointments for a given doctor and the current week is retrieved from appointments table, and also the doctor's working hours.

With this data, two lists of AvailabilitySlot are created. One from working hours, and the other one from the appointment collection.

A doctors availability is finally given by the difference between both lists

# Improvements
- Run script data.sql at DB initialization stage
- Run maven from docker script
- Add POST methods to add doctors, patients and working hours
- Add logs
