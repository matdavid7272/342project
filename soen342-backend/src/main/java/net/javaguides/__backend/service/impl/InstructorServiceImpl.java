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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    @Override
    public InstructorDto createInstructor(InstructorDto instructorDto) {
        Instructor instructor = InstructorMapper.mapToInstructor(instructorDto);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return InstructorMapper.mapToInstructorDto(savedInstructor);
    }

    @Override
    public InstructorDto getInstructorById(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId);
        if (instructor == null) {
            throw new ResourceNotFoundException("Instructor with id " + instructorId + " does not exist");
        }
        return InstructorMapper.mapToInstructorDto(instructor);
    }

    @Override
    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepository.findById(id);
        if (instructor == null) {
            throw new ResourceNotFoundException("Instructor with id " + id + " does not exist");
        }

        boolean deleted = instructorRepository.deleteInstructor(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Instructor with id " + id + " could not be deleted");
        }
    }

    @Override
    public InstructorDto updateInstructor(Long id, InstructorDto updatedInstructorDto) {
        Instructor existingInstructor = instructorRepository.findById(id);
        if (existingInstructor == null) {
            throw new ResourceNotFoundException("Instructor with id " + id + " does not exist");
        }

        Instructor updatedInstructor = InstructorMapper.mapToInstructor(updatedInstructorDto);

        // Update specialization if available
        existingInstructor.setFirstname(updatedInstructor.getFirstname());
        existingInstructor.setLastname(updatedInstructor.getLastname());
        existingInstructor.setEmail(updatedInstructor.getEmail());
        existingInstructor.setAge(updatedInstructor.getAge());
        existingInstructor.setSpecialization(updatedInstructor.getSpecialization());

        Instructor savedInstructor = instructorRepository.editInstructor(id, existingInstructor);  // Assuming editInstructor saves and returns updated instructor
        return InstructorMapper.mapToInstructorDto(savedInstructor);
    }

    @Override
    public List<InstructorDto> getAllInstructors() {
        List<Instructor> instructors = instructorRepository.findAll();
        return instructors.stream()
                .map(InstructorMapper::mapToInstructorDto)
                .collect(Collectors.toList());
    }
}
