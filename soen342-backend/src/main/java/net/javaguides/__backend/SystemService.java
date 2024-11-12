package net.javaguides.__backend;

import net.javaguides.__backend.controller.LessonController;
import net.javaguides.__backend.dto.LessonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import net.javaguides.__backend.dto.ClientDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import net.javaguides.__backend.service.ClientService;
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
}
