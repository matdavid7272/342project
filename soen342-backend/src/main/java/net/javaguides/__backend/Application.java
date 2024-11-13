package net.javaguides.__backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = "net.javaguides.__backend")
public class Application implements CommandLineRunner {

	private final SystemService systemService;

	@Autowired
	public Application(SystemService systemService) {
		this.systemService = systemService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("===== Main Menu =====");
			System.out.println("1. View Available Lessons");
			System.out.println("2. Client Menu");
			System.out.println("3. Instructor Menu");
			System.out.println("4. Administrator Menu");
			System.out.println("5. Exit");

			System.out.print("Select an option: ");
			int option = scanner.nextInt();

			switch (option) {
				case 1 -> systemService.displayAllLessons();
				case 2 -> clientMenu(scanner);
				case 5 -> {
					System.out.println("Exiting...");
					scanner.close();
					System.exit(option);
					;
				}
				default -> System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private void clientMenu(Scanner scanner) {
		System.out.println("===== Client Menu =====");
		System.out.println("1. Register as New Client");
		System.out.println("2. View Available Offerings");
		System.out.println("3. Make a Booking");
		System.out.println("4. View My Bookings");
		System.out.println("5. Cancel a Booking");
		System.out.println("6. Return to Main Menu");

		int option = scanner.nextInt();
		scanner.nextLine();

		switch (option) {
			case 1 -> systemService.registerClient(scanner);
			case 2 -> systemService.displayActiveOfferings();
			case 6 -> System.out.println("Returning to Main Menu");
			default -> System.out.println("Invalid choice. Please try again.");
		}
	}

	private void instructorMenu(Scanner scanner) {
		System.out.println("===== Instructor Menu =====");
		System.out.println("1. Create Instructor");
		System.out.println("2. View Available Offerings");
		System.out.println("3. Register for an Offering");
		System.out.println("4. View My Offerings");
		System.out.println("5. Return to Main Menu");

		int option = scanner.nextInt();
		scanner.nextLine();

		switch (option) {
			case 1 -> systemService.createInstructor(scanner);
			case 2 -> systemService.displayActiveOfferings();
			case 3 -> systemService.registerOffering(scanner);
			case 4 -> systemService.viewMyOfferings();
			case 5 -> System.out.println("Returning to Main Menu");
			default -> System.out.println("Invalid choice. Please try again.");
		}
	}

}
