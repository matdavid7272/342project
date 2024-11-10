package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    // Additional custom queries, if any, can be added here.
}
