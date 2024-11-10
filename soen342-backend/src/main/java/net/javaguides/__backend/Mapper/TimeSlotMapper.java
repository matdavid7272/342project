package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.entity.TimeSlot;

import java.sql.Time;
import java.time.LocalTime;

public class TimeSlotMapper {

    public static TimeSlotDto mapToTimeSlotDto(TimeSlot timeSlot) {
        return new TimeSlotDto(
                timeSlot.getId(),
                timeSlot.getDay(),
                timeSlot.getStartTime().toString(),
                timeSlot.getEndTime().toString(),
                timeSlot.getSchedule() != null ? timeSlot.getSchedule().getId() : null
        );
    }

    public static TimeSlot mapToTimeSlot(TimeSlotDto timeSlotDto) {
        return new TimeSlot(
                timeSlotDto.getId(),
                timeSlotDto.getDay(),
                LocalTime.parse(timeSlotDto.getStartTime()), // Parse String to LocalTime
                LocalTime.parse(timeSlotDto.getEndTime()),   // Assuming endTime is a string representation
                null  // Set schedule based on ID later
        );
    }
}
