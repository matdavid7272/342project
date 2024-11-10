package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.entity.TimeSlot;
import net.javaguides.__backend.Mapper.TimeSlotMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.TimeSlotRepository;
import net.javaguides.__backend.service.TimeSlotService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Override
    public TimeSlotDto createTimeSlot(TimeSlotDto timeSlotDto) {
        TimeSlot timeSlot = TimeSlotMapper.mapToTimeSlot(timeSlotDto);
        TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);
        return TimeSlotMapper.mapToTimeSlotDto(savedTimeSlot);
    }

    @Override
    public TimeSlotDto getTimeSlotById(Long timeSlotId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId);
        if (timeSlot == null) {
            throw new ResourceNotFoundException("TimeSlot with id " + timeSlotId + " does not exist");
        }
        return TimeSlotMapper.mapToTimeSlotDto(timeSlot);
    }

    @Override
    public void deleteTimeSlot(Long id) {
        TimeSlot timeSlot = timeSlotRepository.findById(id);
        if (timeSlot == null) {
            throw new ResourceNotFoundException("TimeSlot with id " + id + " does not exist");
        }

        boolean deleted = timeSlotRepository.deleteTimeSlot(id);
        if (!deleted) {
            throw new ResourceNotFoundException("TimeSlot with id " + id + " could not be deleted");
        }
    }

    @Override
    public TimeSlotDto updateTimeSlot(Long id, TimeSlotDto updatedTimeSlotDto) {
        TimeSlot existingTimeSlot = timeSlotRepository.findById(id);
        if (existingTimeSlot == null) {
            throw new ResourceNotFoundException("TimeSlot with id " + id + " does not exist");
        }

        // Map updatedTimeSlotDto to TimeSlot object and set fields in existingTimeSlot
        TimeSlot updatedTimeSlot = TimeSlotMapper.mapToTimeSlot(updatedTimeSlotDto);

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
