package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.entity.TimeSlot;
import net.javaguides.__backend.Mapper.TimeSlotMapper; // Assuming you have a TimeSlotMapper for conversion
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.TimeSlotRepository;
import net.javaguides.__backend.service.TimeSlotService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Override
    public TimeSlotDto createTimeSlot(TimeSlotDto timeSlotDto) {
        // Convert TimeSlotDto to TimeSlot entity
        TimeSlot timeSlot = TimeSlotMapper.mapToTimeSlot(timeSlotDto);
        // Save the timeSlot entity
        TimeSlot savedTimeSlot = timeSlotRepository.save(timeSlot);
        // Convert saved timeSlot back to DTO
        return TimeSlotMapper.mapToTimeSlotDto(savedTimeSlot);
    }

    @Override
    public TimeSlotDto getTimeSlotById(Long timeSlotId) {
        // Try to find the timeSlot by ID
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(timeSlotId);
        if (!timeSlotOptional.isPresent()) {
            // Handle case where timeSlot is not found
            throw new ResourceNotFoundException("TimeSlot with id " + timeSlotId + " does not exist");
        }
        TimeSlot timeSlot = timeSlotOptional.get();
        return TimeSlotMapper.mapToTimeSlotDto(timeSlot);
    }

    @Override
    public void deleteTimeSlot(Long id) {
        // Try to find the timeSlot by ID
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(id);
        if (!timeSlotOptional.isPresent()) {
            // Handle case where timeSlot is not found
            throw new ResourceNotFoundException("TimeSlot with id " + id + " does not exist");
        }
        // Delete the found timeSlot
        timeSlotRepository.delete(timeSlotOptional.get());
    }

    @Override
    public TimeSlotDto updateTimeSlot(Long id, TimeSlotDto updatedTimeSlotDto) {
        // Try to find the timeSlot by ID
        Optional<TimeSlot> existingTimeSlotOptional = timeSlotRepository.findById(id);
        if (!existingTimeSlotOptional.isPresent()) {
            // Handle case where timeSlot is not found
            throw new ResourceNotFoundException("TimeSlot with id " + id + " does not exist");
        }

        // Convert updated DTO to entity
        TimeSlot updatedTimeSlot = TimeSlotMapper.mapToTimeSlot(updatedTimeSlotDto);
        updatedTimeSlot.setId(id);  // Ensure the updated timeSlot has the correct ID

        // Save the updated timeSlot
        TimeSlot savedTimeSlot = timeSlotRepository.save(updatedTimeSlot);
        return TimeSlotMapper.mapToTimeSlotDto(savedTimeSlot);
    }

    @Override
    public List<TimeSlotDto> getAllTimeSlots() {
        // Get all timeSlots from the repository
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        // Convert timeSlots to DTOs and return as a list
        return timeSlots.stream()
                .map(TimeSlotMapper::mapToTimeSlotDto)
                .collect(Collectors.toList());
    }
}
