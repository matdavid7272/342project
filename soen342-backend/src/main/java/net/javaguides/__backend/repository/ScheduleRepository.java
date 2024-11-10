package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // You can add custom query methods here, for example:
    // List<Schedule> findByLocationId(Long locationId);
}
