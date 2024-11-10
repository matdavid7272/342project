package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.ScheduleDto;
import net.javaguides.__backend.entity.Schedule;
import net.javaguides.__backend.entity.TimeSlot;
import net.javaguides.__backend.entity.Location;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.TimeSlotRepository;
import net.javaguides.__backend.repository.LocationRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleMapper {

    private final TimeSlotRepository timeSlotRepository;
    private final LocationRepository locationRepository;

    public ScheduleMapper(TimeSlotRepository timeSlotRepository, LocationRepository locationRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.locationRepository = locationRepository;
    }

    public ScheduleDto mapToScheduleDto(Schedule schedule) {
        return new ScheduleDto(
                schedule.getId(),
                schedule.getTimeSlots() != null ? schedule.getTimeSlots().stream().map(ts -> ts.getId()).collect(Collectors.toList()) : null,
                schedule.getLocation() != null ? schedule.getLocation().getId() : null
        );
    }

    public Schedule mapToSchedule(ScheduleDto scheduleDto) {
        // Fetch TimeSlots from the database using the provided IDs
        List<TimeSlot> timeSlots = timeSlotRepository.findAllById(scheduleDto.getTimeSlotIds());

        // Fetch Location entity from the database using the provided location ID
        Location location = locationRepository.findById(scheduleDto.getLocationId()).orElseThrow(
                () -> new ResourceNotFoundException("Location with ID " + scheduleDto.getLocationId() + " not found"));

        // Return new Schedule with loaded TimeSlots and Location
        return new Schedule(
                scheduleDto.getId(),
                timeSlots, // Now you have the actual TimeSlot entities
                location // Now you have the actual Location entity
        );
    }
}
