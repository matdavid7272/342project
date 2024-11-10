package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.ScheduleDto;
import net.javaguides.__backend.entity.Schedule;

public class ScheduleMapper {

    public static ScheduleDto mapToScheduleDto(Schedule schedule) {
        return new ScheduleDto(
                schedule.getId(),
                schedule.getTimeSlots() != null ? schedule.getTimeSlots().stream().map(ts -> ts.getId()).toList() : null,
                schedule.getLocation() != null ? schedule.getLocation().getId() : null
        );
    }

    public static Schedule mapToSchedule(ScheduleDto scheduleDto) {
        return new Schedule(
                scheduleDto.getId(),
                null, // Need to load timeSlots based on IDs from the DB
                null  // Need to load location based on ID from the DB
        );
    }
}
