package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.ScheduleDto;

import java.util.List;

public interface ScheduleService {

    ScheduleDto createSchedule(ScheduleDto scheduleDto);

    ScheduleDto getScheduleById(Long scheduleId);

    void deleteSchedule(Long id);

    ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto);

    List<ScheduleDto> getAllSchedules();
}