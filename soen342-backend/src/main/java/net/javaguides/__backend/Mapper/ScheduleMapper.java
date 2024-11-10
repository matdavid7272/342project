package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.ScheduleDto;
import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.entity.Schedule;
import net.javaguides.__backend.entity.TimeSlot;

import java.util.List;
import java.util.stream.Collectors;

public class ScheduleMapper {

    public static ScheduleDto mapToScheduleDto(Schedule schedule) {
        List<TimeSlotDto> timeSlotDtos = schedule.getTimeSlots().stream()
                .map(ScheduleMapper::mapToTimeSlotDto)
                .collect(Collectors.toList());

        return new ScheduleDto(
                schedule.getId(),
                timeSlotDtos
        );
    }

    public static Schedule mapToSchedule(ScheduleDto scheduleDto) {
        List<TimeSlot> timeSlots = scheduleDto.getTimeSlots().stream()
                .map(ScheduleMapper::mapToTimeSlot)
                .collect(Collectors.toList());

        return new Schedule(
                scheduleDto.getId(),
                timeSlots
        );
    }

    private static TimeSlotDto mapToTimeSlotDto(TimeSlot timeSlot) {
        return new TimeSlotDto(
                timeSlot.getId(),
                timeSlot.getStartTime(),
                timeSlot.getEndTime()
        );
    }

    private static TimeSlot mapToTimeSlot(TimeSlotDto timeSlotDto) {
        return new TimeSlot(
                timeSlotDto.getId(),
                timeSlotDto.getStartTime(),
                timeSlotDto.getEndTime()
        );
    }
}
