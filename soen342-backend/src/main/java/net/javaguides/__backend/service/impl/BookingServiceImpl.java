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
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    // ReadWriteLock to manage access to the shared resource
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        lock.writeLock().lock(); // Acquire the write lock

        try {
            Booking booking = bookingMapper.mapToBooking(bookingDto);
            Booking savedBooking = bookingRepository.save(booking);
            return bookingMapper.mapToBookingDto(savedBooking);
        } finally {
            lock.writeLock().unlock(); // Release the write lock
        }
    }

    @Override
    public BookingDto getBookingById(Long bookingId) {
        lock.readLock().lock(); // Acquire the read lock

        try {
            Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
            if (!bookingOptional.isPresent()) {
                throw new ResourceNotFoundException("Booking with id " + bookingId + " does not exist");
            }
            Booking booking = bookingOptional.get();
            return bookingMapper.mapToBookingDto(booking);
        } finally {
            lock.readLock().unlock(); // Release the read lock
        }
    }

    @Override
    public void deleteBooking(Long id) {
        lock.writeLock().lock(); // Acquire the write lock

        try {
            Optional<Booking> bookingOptional = bookingRepository.findById(id);
            if (!bookingOptional.isPresent()) {
                throw new ResourceNotFoundException("Booking with id " + id + " does not exist");
            }
            bookingRepository.delete(bookingOptional.get());
        } finally {
            lock.writeLock().unlock(); // Release the write lock
        }
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto updatedBookingDto) {
        lock.writeLock().lock(); // Acquire the write lock

        try {
            Optional<Booking> existingBookingOptional = bookingRepository.findById(id);
            if (!existingBookingOptional.isPresent()) {
                throw new ResourceNotFoundException("Booking with id " + id + " does not exist");
            }

            Booking updatedBooking = bookingMapper.mapToBooking(updatedBookingDto);
            updatedBooking.setId(id); // Ensure the updated booking has the correct ID

            Booking savedBooking = bookingRepository.save(updatedBooking);
            return bookingMapper.mapToBookingDto(savedBooking);
        } finally {
            lock.writeLock().unlock(); // Release the write lock
        }
    }

    @Override
    public List<BookingDto> getAllBookings() {
        lock.readLock().lock(); // Acquire the read lock

        try {
            List<Booking> bookings = bookingRepository.findAll();
            return bookings.stream()
                    .map(bookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock(); // Release the read lock
        }
    }

    @Override
    public List<BookingDto> getBookingsByClientId(Long clientId) {
        lock.readLock().lock(); // Acquire the read lock

        try {
            List<Booking> bookings = bookingRepository.findByClientId(clientId);

            if (bookings.isEmpty()) {
                throw new ResourceNotFoundException("No bookings found for client with id " + clientId);
            }

            return bookings.stream()
                    .map(bookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock(); // Release the read lock
        }
    }
}
