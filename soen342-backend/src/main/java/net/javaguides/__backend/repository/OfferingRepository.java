package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {
    // Add the method to fetch offerings by instructorId
    List<Offering> findByInstructorId(Long instructorId);

    Offering findByIdAndInstructorId(Long id, Long instructorId);
}
