package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.ScheduleDto;
import net.javaguides.__backend.entity.Schedule;
import net.javaguides.__backend.Mapper.ScheduleMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.ScheduleRepository;
import net.javaguides.__backend.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;  // Inject ScheduleMapper

    @Override
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
        // Convert ScheduleDto to Schedule entity using injected ScheduleMapper
        Schedule schedule = scheduleMapper.mapToSchedule(scheduleDto);
        // Save the schedule entity
        Schedule savedSchedule = scheduleRepository.save(schedule);
        // Convert saved schedule back to DTO
        return scheduleMapper.mapToScheduleDto(savedSchedule);
    }

    @Override
    public ScheduleDto getScheduleById(Long scheduleId) {
        // Try to find the schedule by ID
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        if (!scheduleOptional.isPresent()) {
            // Handle case where schedule is not found
            throw new ResourceNotFoundException("Schedule with id " + scheduleId + " does not exist");
        }
        Schedule schedule = scheduleOptional.get();
        return scheduleMapper.mapToScheduleDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        // Try to find the schedule by ID
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (!scheduleOptional.isPresent()) {
            // Handle case where schedule is not found
            throw new ResourceNotFoundException("Schedule with id " + id + " does not exist");
        }
        // Delete the found schedule
        scheduleRepository.delete(scheduleOptional.get());
    }

    @Override
    public ScheduleDto updateSchedule(Long id, ScheduleDto updatedScheduleDto) {
        // Try to find the schedule by ID
        Optional<Schedule> existingScheduleOptional = scheduleRepository.findById(id);
        if (!existingScheduleOptional.isPresent()) {
            // Handle case where schedule is not found
            throw new ResourceNotFoundException("Schedule with id " + id + " does not exist");
        }

        // Convert updated DTO to entity using injected ScheduleMapper
        Schedule updatedSchedule = scheduleMapper.mapToSchedule(updatedScheduleDto);
        updatedSchedule.setId(id);  // Ensure the updated schedule has the correct ID

        // Save the updated schedule
        Schedule savedSchedule = scheduleRepository.save(updatedSchedule);
        return scheduleMapper.mapToScheduleDto(savedSchedule);
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        // Get all schedules from the repository
        List<Schedule> schedules = scheduleRepository.findAll();
        // Convert schedules to DTOs and return as a list
        return schedules.stream()
                .map(scheduleMapper::mapToScheduleDto)
                .collect(Collectors.toList());
    }
}
