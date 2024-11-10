package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.BookingDto;
import net.javaguides.__backend.entity.Booking;
import net.javaguides.__backend.entity.Client;
import net.javaguides.__backend.entity.Offering;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.ClientRepository;
import net.javaguides.__backend.repository.OfferingRepository;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    private final ClientRepository clientRepository;
    private final OfferingRepository offeringRepository;

    public BookingMapper(ClientRepository clientRepository, OfferingRepository offeringRepository) {
        this.clientRepository = clientRepository;
        this.offeringRepository = offeringRepository;
    }

    public BookingDto mapToBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getClient() != null ? booking.getClient().getId() : null,
                booking.getOffering() != null ? booking.getOffering().getId() : null,
                booking.isActive()
        );
    }

    public Booking mapToBooking(BookingDto bookingDto) {
        // Fetch Client entity from the database using the provided client ID
        Client client = clientRepository.findById(bookingDto.getClientId()).orElseThrow(
                () -> new ResourceNotFoundException("Client with ID " + bookingDto.getClientId() + " not found"));

        // Fetch Offering entity from the database using the provided offering ID
        Offering offering = offeringRepository.findById(bookingDto.getOfferingId()).orElseThrow(
                () -> new ResourceNotFoundException("Offering with ID " + bookingDto.getOfferingId() + " not found"));

        // Return a new Booking with loaded Client and Offering
        return new Booking(
                bookingDto.getId(),
                client,
                offering,
                bookingDto.isActive()
        );
    }
}
