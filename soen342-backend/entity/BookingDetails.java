package net.javaguides.__backend.entity;

import jakarta.persistence.*;

@Entity
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bookingDetails;

    // Many BookingDetails can belong to one Client
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Many BookingDetails can belong to one LessonOffering
    @ManyToOne
    @JoinColumn(name = "lesson_offering_id")
    private LessonOffering lessonOffering;

    // Default constructor
    public BookingDetails() {}

    // Constructor with booking details, client, and lesson offering
    public BookingDetails(String bookingDetails, Client client, LessonOffering lessonOffering) {
        this.bookingDetails = bookingDetails;
        this.client = client;
        this.lessonOffering = lessonOffering;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(String bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LessonOffering getLessonOffering() {
        return lessonOffering;
    }

    public void setLessonOffering(LessonOffering lessonOffering) {
        this.lessonOffering = lessonOffering;
    }
}
