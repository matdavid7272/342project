package net.javaguides.__backend;

import net.javaguides.__backend.controller.LessonController;
import net.javaguides.__backend.dto.LessonDto;
import net.javaguides.__backend.dto.LocationDto;
import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.entity.Instructor;
import net.javaguides.__backend.entity.Lesson;
import net.javaguides.__backend.entity.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import net.javaguides.__backend.dto.ClientDto;
import net.javaguides.__backend.dto.InstructorDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import net.javaguides.__backend.service.ClientService;
import net.javaguides.__backend.service.OfferingService;
import net.javaguides.__backend.service.LessonService;
import net.javaguides.__backend.service.InstructorService;
import net.javaguides.__backend.service.TimeSlotService;
import net.javaguides.__backend.service.LocationService;
import net.javaguides.__backend.entity.Location;

import java.util.Scanner;

import java.util.List;

@Service
public class SystemService {

    @Autowired
    private LessonController lessonController;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OfferingService offeringService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private TimeSlotService timeSlotService;
    @Autowired
    private LocationService locationService;

    public void displayAllLessons() {
        // Call the controller method directly (not recommended, though works)
        ResponseEntity<List<LessonDto>> response = lessonController.getAllLessons();
        List<LessonDto> lessons = response.getBody();
        if (lessons != null) {
            lessons.forEach(lesson -> {
                System.out.println("ID: " + lesson.getId() +
                        ", Name: " + lesson.getName() +
                        ", Duration: " + lesson.getDuration() +
                        ", Group: " + lesson.isGroup());
            });
        } else {
            System.out.println("No lessons found.");
        }
    }

    public void registerClient(Scanner scanner) {
        System.out.println("Please enter the following client details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline character after int input

        // Create a new ClientDto object with the input
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstname(firstName);
        clientDto.setLastname(lastName);
        clientDto.setEmail(email);
        clientDto.setAge(age);

        try {
            // Call the service to register the client
            ClientDto savedClient = clientService.createClient(clientDto);

            // Output confirmation of the saved client
            System.out.println("\nClient added successfully!");
            System.out.println("Client Details:");
            System.out.println("ID: " + savedClient.getId());
            System.out.println("First Name: " + savedClient.getFirstname());
            System.out.println("Last Name: " + savedClient.getLastname());
            System.out.println("Email: " + savedClient.getEmail());
            System.out.println("Age: " + savedClient.getAge());

        } catch (RuntimeException e) {
            // Handle any exceptions, such as email duplication
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayActiveOfferings() {
        // Retrieve all offerings
        List<OfferingDto> allOfferings = offeringService.getAllOfferings();

        // Filter for active offerings
        List<OfferingDto> activeOfferings = allOfferings.stream()
                .filter(OfferingDto::isAvailable)
                .toList();

        // Display active offerings with full details
        if (!activeOfferings.isEmpty()) {
            System.out.println("Active Offerings:");
            activeOfferings.forEach(offering -> {
                // Retrieve detailed information for each associated entity using their IDs
                LessonDto lesson = lessonService.getLessonById(offering.getLessonId());
                InstructorDto instructor = instructorService.getInstructorById(offering.getInstructorId());
                TimeSlotDto timeSlot = timeSlotService.getTimeSlotById(offering.getTimeSlotId());
                LocationDto location = locationService.getLocationById(offering.getLocationId());

                // Display the detailed information
                System.out.println("Offering ID: " + offering.getId() +
                        "\nLesson: " + lesson.getName() +
                        "\nInstructor: " + instructor.getFirstname() + " " + instructor.getLastname() +
                        "\nTime Slot: " + timeSlot.getStartTime() + " - " + timeSlot.getEndTime() +
                        "\nLocation: " + location.getCity() + " - " + location.getName() +
                        "\nAvailable: " + offering.isAvailable() + "\n");
            });
        } else {
            System.out.println("No active offerings found.");
        }
    }

}
