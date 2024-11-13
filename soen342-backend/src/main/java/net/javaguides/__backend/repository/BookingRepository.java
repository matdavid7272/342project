package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Custom query methods:
    List<Booking> findByClientId(Long clientId); // Find bookings by client ID

    List<Booking> findByOfferingId(Long offeringId); // Find bookings by offering ID

    List<Booking> findByIsActive(boolean isActive); // Find active or inactive bookings

    Boolean existsByClientId(Long clientId);

    @Transactional
    void deleteByClientId(Long clientId);
}
