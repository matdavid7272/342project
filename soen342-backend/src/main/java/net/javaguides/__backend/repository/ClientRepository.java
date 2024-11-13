package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Additional custom queries, if any, can be added here.
    Optional<Client> findByEmail(String email);
}
