package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // Additional custom queries, if any, can be added here.
    Optional<Instructor> findByEmail(String email);
}
