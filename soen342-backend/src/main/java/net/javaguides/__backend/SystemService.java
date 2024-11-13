package net.javaguides.__backend;

import net.javaguides.__backend.controller.LessonController;
import net.javaguides.__backend.dto.LessonDto;
import net.javaguides.__backend.dto.LocationDto;
import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.dto.TimeSlotDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import net.javaguides.__backend.dto.BookingDto;
import net.javaguides.__backend.dto.ClientDto;
import net.javaguides.__backend.dto.GuardianDto;
import net.javaguides.__backend.dto.InstructorDto;

import net.javaguides.__backend.service.ClientService;
import net.javaguides.__backend.service.OfferingService;
import net.javaguides.__backend.service.LessonService;
import net.javaguides.__backend.service.InstructorService;
import net.javaguides.__backend.service.TimeSlotService;
import net.javaguides.__backend.service.LocationService;
import net.javaguides.__backend.service.GuardianService;
import net.javaguides.__backend.service.BookingService;

import java.util.Scanner;

import java.util.List;

@Service
public class SystemService {

    @Autowired
    private LessonController lessonController;
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
    @Autowired
    private GuardianService guardianService;
    @Autowired
    private BookingService bookingService;

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

    public void makeBooking(Scanner scanner) {
        System.out.print("Enter client email: ");
        String clientEmail = scanner.nextLine();

        // Retrieve the client details by email
        ClientDto client = clientService.getClientByEmail(clientEmail);
        if (client == null) {
            System.out.println("Client not found!");
            return;
        }

        GuardianDto guardian = null; // Will hold the guardian's details if required
        if (client.getAge() < 18) {
            System.out.println("Client is under 18. A guardian's approval is required.");
            System.out.print("Enter guardian email: ");
            String guardianEmail = scanner.nextLine();

            // Check if the guardian already exists by email
            guardian = guardianService.getGuardianByEmail(guardianEmail);

            if (guardian == null) {
                // If the guardian doesn't exist, register them
                System.out.println("Guardian not found. Please register the guardian.");
                System.out.print("Enter guardian first name: ");
                String guardianFirstName = scanner.nextLine();

                System.out.print("Enter guardian last name: ");
                String guardianLastName = scanner.nextLine();

                System.out.print("Enter guardian's age: ");
                int guardianAge = scanner.nextInt();

                // Create a new GuardianDto object
                GuardianDto newGuardian = new GuardianDto();
                newGuardian.setFirstname(guardianFirstName);
                newGuardian.setLastname(guardianLastName);
                newGuardian.setEmail(guardianEmail);
                newGuardian.setAge(guardianAge);

                // Save the new guardian
                guardian = guardianService.createGuardian(newGuardian);
                System.out.println("Guardian registered successfully!");
            }
            System.out.println("Guardian: " + guardian.getFirstname() + " " + guardian.getLastname());
        }

        System.out.print("Enter offering ID to book: ");
        Long offeringId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character

        OfferingDto offering = offeringService.getOfferingById(offeringId);
        if (offering == null) {
            System.out.println("Offering not found!");
            return;
        }

        BookingDto bookingDto = new BookingDto();
        bookingDto.setClientId(client.getId()); // Using the client's ID
        bookingDto.setOfferingId(offeringId);
        bookingDto.setActive(true);

        try {
            BookingDto createdBooking = bookingService.createBooking(bookingDto);
            System.out.println("Booking created successfully!");
            System.out.println("Booking Details:");
            System.out.println("Client ID: " + createdBooking.getClientId());
            System.out.println("Offering ID: " + createdBooking.getOfferingId());
            System.out.println("Active: " + createdBooking.isActive());
        } catch (Exception e) {
            System.out.println("Error creating booking: " + e.getMessage());
        }
    }

    public void createInstructor(Scanner scanner) {
        System.out.println("Please enter the following instructor details:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Specialization: ");
        String specialization = scanner.nextLine();

        InstructorDto instructorDto = new InstructorDto();
        instructorDto.setFirstname(firstName);
        instructorDto.setLastname(lastName);
        instructorDto.setEmail(email);
        instructorDto.setSpecialization(specialization);

        try {
            InstructorDto savedInstructor = instructorService.createInstructor(instructorDto);
            System.out.println("\nInstructor created successfully!");
            System.out.println("Instructor ID: " + savedInstructor.getId());
            System.out.println("Name: " + savedInstructor.getFirstname() + " " + savedInstructor.getLastname());
            System.out.println("Email: " + savedInstructor.getEmail());
            System.out.println("Specialization: " + savedInstructor.getSpecialization());
        } catch (RuntimeException e) {
            System.out.println("Error creating instructor: " + e.getMessage());
        }
    }

    /**
     * Register an instructor for a specific offering.
     */
    public void registerOffering(Scanner scanner) {
        System.out.print("Enter Instructor ID: ");
        long instructorId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Offering ID: ");
        long offeringId = scanner.nextLong();
        scanner.nextLine(); // Consume newline

        try {
            boolean registrationSuccess = offeringService.registerInstructorForOffering(instructorId, offeringId);
            if (registrationSuccess) {
                System.out.println("Instructor successfully registered for the offering.");
            } else {
                System.out.println(
                        "Registration failed. Offering may be unavailable or instructor may already be registered.");
            }
        } catch (RuntimeException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    /**
     * Display offerings assigned to a specific instructor.
     */
    public void viewMyOfferings(Scanner scanner) {
        System.out.print("Enter Instructor ID: ");
        long instructorId = scanner.nextLong();

        List<OfferingDto> instructorOfferings = offeringService.getOfferingsByInstructorId(instructorId);

        if (!instructorOfferings.isEmpty()) {
            System.out.println("Offerings for Instructor ID " + instructorId + ":");
            instructorOfferings.forEach(offering -> {
                LessonDto lesson = lessonService.getLessonById(offering.getLessonId());
                TimeSlotDto timeSlot = timeSlotService.getTimeSlotById(offering.getTimeSlotId());
                LocationDto location = locationService.getLocationById(offering.getLocationId());

                System.out.println("Offering ID: " + offering.getId() +
                        "\nLesson: " + lesson.getName() +
                        "\nTime Slot: " + timeSlot.getStartTime() + " - " + timeSlot.getEndTime() +
                        "\nLocation: " + location.getCity() + " - " + location.getName() +
                        "\nAvailable: " + offering.isAvailable() + "\n");
            });
        } else {
            System.out.println("No offerings found for Instructor ID " + instructorId + ".");
        }
    }

    public void viewMyBookings(Scanner scanner) {
        System.out.print("Enter ID: ");
        Long clientId = scanner.nextLong();

        List<BookingDto> clientBookings = bookingService.getBookingsByClientId(clientId);

        if (!clientBookings.isEmpty()) {
            System.out.println("Bookings for Client ID " + clientId + ":");
            clientBookings.forEach(booking -> {
                OfferingDto offering = offeringService.getOfferingById(booking.getOfferingId());
                LessonDto lesson = lessonService.getLessonById(offering.getLessonId());
                TimeSlotDto timeSlot = timeSlotService.getTimeSlotById(offering.getTimeSlotId());
                LocationDto location = locationService.getLocationById(offering.getLocationId());

                System.out.println("Booking ID: " + booking.getId() +
                        "\nLesson name: " + lesson.getName() +
                        "\nTime Slot: " + timeSlot.getStartTime() + " - " + timeSlot.getEndTime() +
                        "\nLocation: " + location.getCity() + " - " + location.getName());
            });
        } else {
            System.out.println("No bookings found for Client Id " + clientId + ".");
        }
    }

    public void cancelBooking(Scanner scanner) {
        System.out.print("Enter Booking ID to cancel: ");
        Long bookingId = scanner.nextLong();
        scanner.nextLine();

        BookingDto booking = bookingService.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("Booking not found!");
            return;
        }

        if (!booking.isActive()) {
            System.out.println("Booking is already cancelled.");
            return;
        }

        // Proceed to cancel the booking
        booking.setActive(false); // Mark booking as inactive
        try {
            // Update the booking status in the service layer
            BookingDto updatedBooking = bookingService.updateBooking(bookingId, booking);
            System.out.println("Booking cancelled successfully!");
            System.out.println("Booking Details:");
            System.out.println("Client ID: " + updatedBooking.getClientId());
            System.out.println("Offering ID: " + updatedBooking.getOfferingId());
            System.out.println("Active: " + updatedBooking.isActive());
        } catch (Exception e) {
            System.out.println("Error cancelling booking: " + e.getMessage());
        }
    }

    public void viewAllClients() {
        List<ClientDto> allClients = clientService.getAllClients();

        if (!allClients.isEmpty()) {
            System.out.println("All Clients:");
            allClients.forEach(client -> {
                System.out.println("ID: " + client.getId() +
                        ", Name: " + client.getFirstname() + " " + client.getLastname() +
                        ", Email: " + client.getEmail() +
                        ", Age: " + client.getAge());
            });
        } else {
            System.out.println("No clients found.");
        }
    }

    public void viewAllInstructors() {
        List<InstructorDto> allInstructors = instructorService.getAllInstructors();

        if (!allInstructors.isEmpty()) {
            System.out.println("All Instructors:");
            allInstructors.forEach(instructor -> {
                System.out.println("ID: " + instructor.getId() +
                        ", Name: " + instructor.getFirstname() + " " + instructor.getLastname() +
                        ", Specialization: " + instructor.getSpecialization() +
                        ", Email: " + instructor.getEmail());
            });
        } else {
            System.out.println("No instructors found.");
        }
    }

    public void viewAllGuardians() {
        List<GuardianDto> allGuardians = guardianService.getAllGuardians();

        if (!allGuardians.isEmpty()) {
            System.out.println("All Instructors:");
            allGuardians.forEach(instructor -> {
                System.out.println("ID: " + instructor.getId() +
                        ", Name: " + instructor.getFirstname() + " " + instructor.getLastname() +
                        ", Email: " + instructor.getEmail());
            });
        } else {
            System.out.println("No instructors found.");
        }
    }

    public void deleteClient(Scanner scanner) {
        System.out.print("Enter Client ID to delete: ");
        Long clientId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character

        ClientDto client = clientService.getClientById(clientId);

        if (client == null) {
            System.out.println("Client not found!");
            return;
        }

        boolean hasBookings = clientService.hasBookings(clientId); // This is a method you'll need to implement
        if (hasBookings) {
            System.out.println("Client has dependent bookings. Do you want to delete all related bookings? (y/n)");
            String response = scanner.nextLine();
            if ("y".equalsIgnoreCase(response)) {
                // Step 2: Delete dependent records (bookings)
                try {
                    clientService.deleteBookingsByClientId(clientId);
                    System.out.println("Related bookings deleted successfully!");
                } catch (Exception e) {
                    System.out.println("Error deleting related bookings: " + e.getMessage());
                    return; // Exit if there's an issue with deleting bookings
                }
            } else {
                // Optionally, allow updating bookings to dissociate them
                System.out.println("Related bookings will not be deleted.");
                return;
            }
        }

        // Step 3: Proceed to delete the client after handling dependencies
        try {
            clientService.deleteClient(clientId);
            System.out.println("Client deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting client: " + e.getMessage());
        }
    }

    public void deleteInstructor(Scanner scanner) {
        System.out.print("Enter Instructor ID to delete: ");
        Long instructorId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character

        InstructorDto instructor = instructorService.getInstructorById(instructorId);

        if (instructor == null) {
            System.out.println("Instructor not found!");
            return;
        }

        boolean hasOfferings = instructorService.hasOfferings(instructorId); // This is a method you'll need to
                                                                             // implement
        if (hasOfferings) {
            System.out
                    .println("Instructor has dependent offerings. Do you want to delete all related offerings? (y/n)");
            String response = scanner.nextLine();
            if ("y".equalsIgnoreCase(response)) {
                // Step 2: Delete dependent records (bookings)
                try {
                    instructorService.deleteOfferingsByInstructorId(instructorId);
                    System.out.println("Related offerings deleted successfully!");
                } catch (Exception e) {
                    System.out.println("Error deleting related offerings: " + e.getMessage());
                    return; // Exit if there's an issue with deleting bookings
                }
            } else {
                // Optionally, allow updating bookings to dissociate them
                System.out.println("Related offerings will not be deleted.");
                return;
            }
        }

        // Step 3: Proceed to delete the client after handling dependencies
        try {
            instructorService.deleteInstructor(instructorId);
            System.out.println("Instructor deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting instructor: " + e.getMessage());
        }
    }

    public void deleteGuardian(Scanner scanner) {
        System.out.print("Enter Guardian ID to delete: ");
        Long guardianId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character

        GuardianDto guardian = guardianService.getGuardianById(guardianId);

        if (guardian == null) {
            System.out.println("Guardian not found!");
            return;
        }

        try {
            guardianService.deleteGuardian(guardianId);
            System.out.println("Guardian deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting guardian: " + e.getMessage());
        }
    }

    public void addNewOffering(Scanner scanner) {

    }

    public void viewAllBookings() {

    }

}