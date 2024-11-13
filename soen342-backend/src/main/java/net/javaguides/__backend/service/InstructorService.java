package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.InstructorDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InstructorService {

    InstructorDto createInstructor(InstructorDto instructorDto);

    InstructorDto getInstructorById(Long instructorId);

    void deleteInstructor(Long id);

    InstructorDto updateInstructor(Long id, InstructorDto instructorDto);

    List<InstructorDto> getAllInstructors();

    Boolean hasOfferings(Long instructorId);

    @Transactional
    void deleteOfferingsByInstructorId(Long instructorId);
}
