package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    // Additional custom queries, if any, can be added here.
}
