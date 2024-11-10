package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.InstructorDto;
import net.javaguides.__backend.entity.Instructor;

public class InstructorMapper {

    // Map Instructor entity to InstructorDto
    public static InstructorDto mapToInstructorDto(Instructor instructor) {
        return new InstructorDto(
                instructor.getId(),
                instructor.getLastname(),
                instructor.getFirstname(),
                instructor.getEmail(),
                instructor.getAge(),
                instructor.getSpecialization() // Include specialization
        );
    }

    // Map InstructorDto to Instructor entity
    public static Instructor mapToInstructor(InstructorDto instructorDto) {
        // Use the Instructor constructor with super() call to initialize User fields
        return new Instructor(
                instructorDto.getId(),
                instructorDto.getLastname(),
                instructorDto.getFirstname(),
                instructorDto.getEmail(),
                instructorDto.getAge(),
                instructorDto.getSpecialization() // Set specialization for Instructor
        );
    }
}
