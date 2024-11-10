package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.BookingDto;
import net.javaguides.__backend.entity.Booking;
import net.javaguides.__backend.Mapper.BookingMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.BookingRepository;
import net.javaguides.__backend.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;  // Inject BookingMapper

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        // Convert BookingDto to Booking entity using injected BookingMapper
        Booking booking = bookingMapper.mapToBooking(bookingDto);
        // Save the booking entity
        Booking savedBooking = bookingRepository.save(booking);
        // Convert saved booking back to DTO
        return bookingMapper.mapToBookingDto(savedBooking);
    }

    @Override
    public BookingDto getBookingById(Long bookingId) {
        // Try to find the booking by ID
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (!bookingOptional.isPresent()) {
            // Handle case where booking is not found
            throw new ResourceNotFoundException("Booking with id " + bookingId + " does not exist");
        }
        Booking booking = bookingOptional.get();
        return bookingMapper.mapToBookingDto(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        // Try to find the booking by ID
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if (!bookingOptional.isPresent()) {
            // Handle case where booking is not found
            throw new ResourceNotFoundException("Booking with id " + id + " does not exist");
        }
        // Delete the found booking
        bookingRepository.delete(bookingOptional.get());
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto updatedBookingDto) {
        // Try to find the booking by ID
        Optional<Booking> existingBookingOptional = bookingRepository.findById(id);
        if (!existingBookingOptional.isPresent()) {
            // Handle case where booking is not found
            throw new ResourceNotFoundException("Booking with id " + id + " does not exist");
        }

        // Convert updated DTO to entity using injected BookingMapper
        Booking updatedBooking = bookingMapper.mapToBooking(updatedBookingDto);
        updatedBooking.setId(id);  // Ensure the updated booking has the correct ID

        // Save the updated booking
        Booking savedBooking = bookingRepository.save(updatedBooking);
        return bookingMapper.mapToBookingDto(savedBooking);
    }

    @Override
    public List<BookingDto> getAllBookings() {
        // Get all bookings from the repository
        List<Booking> bookings = bookingRepository.findAll();
        // Convert bookings to DTOs and return as a list
        return bookings.stream()
                .map(bookingMapper::mapToBookingDto)
                .collect(Collectors.toList());
    }
}
