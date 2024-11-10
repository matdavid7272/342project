package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.InstructorDto;
import net.javaguides.__backend.entity.Instructor;

import java.util.List;

public interface InstructorService {

    InstructorDto createInstructor(InstructorDto instructorDto);

    InstructorDto getInstructorById(Long instructorId);

    void deleteInstructor(Long id);

    InstructorDto updateInstructor(Long id, InstructorDto instructorDto);

    List<InstructorDto> getAllInstructors();
}
