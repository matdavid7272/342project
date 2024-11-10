package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.entity.TimeSlot;

import java.util.List;

public interface TimeSlotService {
    TimeSlotDto createTimeSlot(TimeSlotDto timeSlotDto);

    TimeSlotDto getTimeSlotById(Long timeSlotId);

    void deleteTimeSlot(Long id);

    TimeSlotDto updateTimeSlot(Long id, TimeSlotDto timeSlotDto);

    List<TimeSlotDto> getAllTimeSlots();
}
