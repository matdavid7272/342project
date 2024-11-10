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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = ScheduleMapper.mapToSchedule(scheduleDto);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleMapper.mapToScheduleDto(savedSchedule);
    }

    @Override
    public ScheduleDto getScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId);
        if (schedule == null) {
            throw new ResourceNotFoundException("Schedule with id " + scheduleId + " does not exist");
        }
        return ScheduleMapper.mapToScheduleDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            throw new ResourceNotFoundException("Schedule with id " + id + " does not exist");
        }

        scheduleRepository.deleteSchedule(id);  // Assuming deleteSchedule handles deletion
    }

    @Override
    public ScheduleDto updateSchedule(Long id, ScheduleDto updatedScheduleDto) {
        Schedule existingSchedule = scheduleRepository.findById(id);
        if (existingSchedule == null) {
            throw new ResourceNotFoundException("Schedule with id " + id + " does not exist");
        }

        Schedule updatedSchedule = ScheduleMapper.mapToSchedule(updatedScheduleDto);
//        existingSchedule.setName(updatedSchedule.getName());
//        existingSchedule.setDate(updatedSchedule.getDate());
//        existingSchedule.setTime(updatedSchedule.getTime());

        Schedule savedSchedule = scheduleRepository.save(updatedSchedule);
        return ScheduleMapper.mapToScheduleDto(savedSchedule);
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleMapper::mapToScheduleDto)
                .collect(Collectors.toList());
    }
}
