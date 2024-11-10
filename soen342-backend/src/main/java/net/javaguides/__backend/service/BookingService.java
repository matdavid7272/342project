package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(BookingDto bookingDto);

    BookingDto getBookingById(Long bookingId);

    void deleteBooking(Long id);

    BookingDto updateBooking(Long id, BookingDto bookingDto);

    List<BookingDto> getAllBookings();
}
