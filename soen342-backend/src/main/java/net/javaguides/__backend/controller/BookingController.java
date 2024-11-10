package net.javaguides.__backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.BookingDto;
import net.javaguides.__backend.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    // Create a new booking
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto) {
        BookingDto savedBooking = bookingService.createBooking(bookingDto);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    // Get a booking by ID
    @GetMapping("{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable("id") Long bookingId) {
        BookingDto bookingDto = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(bookingDto);
    }

    // Get all bookings
    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        List<BookingDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    // Update a booking by ID
    @PutMapping("{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable("id") Long bookingId,
                                                    @RequestBody BookingDto updatedBooking) {
        BookingDto bookingDto = bookingService.updateBooking(bookingId, updatedBooking);
        return ResponseEntity.ok(bookingDto);
    }

    // Delete a booking by ID
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
