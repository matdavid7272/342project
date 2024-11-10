package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    // You can add custom query methods here, for example:
    // List<TimeSlot> findByScheduleId(Long scheduleId);
}
