package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.InstructorDto;
import net.javaguides.__backend.entity.Instructor;
import net.javaguides.__backend.Mapper.InstructorMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.InstructorRepository;
import net.javaguides.__backend.service.InstructorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;  // Inject InstructorMapper

    @Override
    public InstructorDto createInstructor(InstructorDto instructorDto) {
        // Check if an instructor with the same email already exists
        Optional<Instructor> existingInstructor = instructorRepository.findByEmail(instructorDto.getEmail());
        if (existingInstructor.isPresent()) {
            // If email exists, throw a custom exception
            throw new ResourceNotFoundException("Email Duplicate");
        }

        // Convert InstructorDto to Instructor entity using injected InstructorMapper
        Instructor instructor = instructorMapper.mapToInstructor(instructorDto);

        // Save the instructor entity
        Instructor savedInstructor = instructorRepository.save(instructor);

        // Convert saved instructor back to DTO
        return instructorMapper.mapToInstructorDto(savedInstructor);
    }

    @Override
    public InstructorDto getInstructorById(Long instructorId) {
        // Try to find the instructor by ID
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if (!instructorOptional.isPresent()) {
            // Handle case where instructor is not found
            throw new ResourceNotFoundException("Instructor with id " + instructorId + " does not exist");
        }
        Instructor instructor = instructorOptional.get();
        return instructorMapper.mapToInstructorDto(instructor);
    }

    @Override
    public void deleteInstructor(Long id) {
        // Try to find the instructor by ID
        Optional<Instructor> instructorOptional = instructorRepository.findById(id);
        if (!instructorOptional.isPresent()) {
            // Handle case where instructor is not found
            throw new ResourceNotFoundException("Instructor with id " + id + " does not exist");
        }
        // Delete the found instructor
        instructorRepository.delete(instructorOptional.get());
    }

    @Override
    public InstructorDto updateInstructor(Long id, InstructorDto updatedInstructorDto) {
        // Try to find the instructor by ID
        Optional<Instructor> existingInstructorOptional = instructorRepository.findById(id);
        if (!existingInstructorOptional.isPresent()) {
            throw new ResourceNotFoundException("Instructor with id " + id + " does not exist");
        }

        // Check if another instructor with the same email exists (excluding the current one)
        Optional<Instructor> instructorWithSameEmail = instructorRepository.findByEmail(updatedInstructorDto.getEmail());
        if (instructorWithSameEmail.isPresent() && !instructorWithSameEmail.get().getId().equals(id)) {
            throw new ResourceNotFoundException("Email Duplicate");
        }

        // Convert updated DTO to entity using injected InstructorMapper
        Instructor updatedInstructor = instructorMapper.mapToInstructor(updatedInstructorDto);
        updatedInstructor.setId(id);

        // Save the updated instructor
        Instructor savedInstructor = instructorRepository.save(updatedInstructor);
        return instructorMapper.mapToInstructorDto(savedInstructor);
    }

    @Override
    public List<InstructorDto> getAllInstructors() {
        // Get all instructors from the repository
        List<Instructor> instructors = instructorRepository.findAll();
        // Convert instructors to DTOs and return as a list
        return instructors.stream()
                .map(instructorMapper::mapToInstructorDto)
                .collect(Collectors.toList());
    }
}
