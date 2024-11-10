package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.entity.TimeSlot;
import net.javaguides.__backend.entity.Schedule;
import net.javaguides.__backend.repository.ScheduleRepository; // Assuming you have this repository

public class TimeSlotMapper {

    private final ScheduleRepository scheduleRepository; // Inject repository to fetch Schedule

    // Constructor to inject repository
    public TimeSlotMapper(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // Map TimeSlot entity to TimeSlotDto
    public static TimeSlotDto mapToTimeSlotDto(TimeSlot timeSlot) {
        return new TimeSlotDto(
                timeSlot.getId(),
                timeSlot.getStartTime(),
                timeSlot.getEndTime(),
                timeSlot.getSchedule() != null ? timeSlot.getSchedule().getId() : null // Get schedule ID if available
        );
    }

    // Map TimeSlotDto to TimeSlot entity
    public TimeSlot mapToTimeSlot(TimeSlotDto timeSlotDto) {
        // Fetch the Schedule entity based on the scheduleId
        Schedule schedule = null;
        if (timeSlotDto.getScheduleId() != null) {
            schedule = scheduleRepository.findById(timeSlotDto.getScheduleId()).orElse(null);
        }

        // Map the TimeSlotDto to the TimeSlot entity
        return new TimeSlot(
                timeSlotDto.getId(),
                timeSlotDto.getStartTime(),
                timeSlotDto.getEndTime(),
                schedule // Set the Schedule entity
        );
    }
}
