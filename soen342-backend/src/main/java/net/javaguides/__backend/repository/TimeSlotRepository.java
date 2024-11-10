package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.entity.TimeSlot;
import net.javaguides.__backend.entity.Schedule;
import net.javaguides.__backend.Mapper.TimeSlotMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.TimeSlotRepository;
import net.javaguides.__backend.repository.ScheduleRepository;
import net.javaguides.__backend.service.TimeSlotService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final ScheduleRepository scheduleRepository;  // Injecting the ScheduleRepository to fetch the associated Schedule

    @Override
    public TimeSlotDto createTimeSlot(TimeSlotDto timeSlotDto) {
        // Fetch the Schedule associated with the timeSlotDto
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(timeSlotDto.getSchedule().getId());
        if (!scheduleOptional.isPresent()) {
            throw new ResourceNotFoundException("Schedule with id " + timeSlotDto.getSchedule().getId() + " does not exist");
        }
        Schedule schedule = scheduleOptional.get();

        // Map TimeSlotDto to TimeSlot entity, including the fetched Schedule
        TimeSlot timeSlot = TimeSlotMapper.mapToTimeSlot(timeSlotDto, schedule);
        TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);

        return TimeSlotMapper.mapToTimeSlotDto(savedTimeSlot);
    }

    @Override
    public TimeSlotDto getTimeSlotById(Long timeSlotId) {
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(timeSlotId);
        if (!timeSlotOptional.isPresent()) {
            throw new ResourceNotFoundException("TimeSlot with id " + timeSlotId + " does not exist");
        }

        return TimeSlotMapper.mapToTimeSlotDto(timeSlotOptional.get());
    }

    @Override
    public void deleteTimeSlot(Long id) {
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(id);
        if (!timeSlotOptional.isPresent()) {
            throw new ResourceNotFoundException("TimeSlot with id " + id + " does not exist");
        }

        boolean deleted = timeSlotRepository.deleteTimeSlot(id);
        if (!deleted) {
            throw new ResourceNotFoundException("TimeSlot with id " + id + " could not be deleted");
        }
    }

    @Override
    public TimeSlotDto updateTimeSlot(Long id, TimeSlotDto updatedTimeSlotDto) {
        Optional<TimeSlot> existingTimeSlotOptional = timeSlotRepository.findById(id);
        if (!existingTimeSlotOptional.isPresent()) {
            throw new ResourceNotFoundException("TimeSlot with id " + id + " does not exist");
        }

        // Fetch the Schedule associated with the updated TimeSlotDto
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(updatedTimeSlotDto.getSchedule().getId());
        if (!scheduleOptional.isPresent()) {
            throw new ResourceNotFoundException("Schedule with id " + updatedTimeSlotDto.getSchedule().getId() + " does not exist");
        }
        Schedule schedule = scheduleOptional.get();

        // Map updatedTimeSlotDto to TimeSlot entity and set the Schedule
        TimeSlot updatedTimeSlot = TimeSlotMapper.mapToTimeSlot(updatedTimeSlotDto, schedule);

        TimeSlot savedTimeSlot = timeSlotRepository.editTimeSlot(id, updatedTimeSlot);
        if (savedTimeSlot == null) {
            throw new ResourceNotFoundException("TimeSlot with id " + id + " could not be updated");
        }

        return TimeSlotMapper.mapToTimeSlotDto(savedTimeSlot);
    }

    @Override
    public List<TimeSlotDto> getAllTimeSlots() {
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        return timeSlots.stream()
                .map(TimeSlotMapper::mapToTimeSlotDto) // Convert each TimeSlot to TimeSlotDto
                .collect(Collectors.toList());
    }
}
