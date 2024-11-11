package net.javaguides.__backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Scanner;

@SpringBootApplication
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
		Scanner scanner = new Scanner(System.in);
		while (true) {
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
				case 5 -> {
					System.out.println("Exiting...");
					scanner.close();
					return;
				}
				default -> System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}
