package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    // Additional custom queries, if any, can be added here.
    Optional<Guardian> findByEmail(String email);
}
