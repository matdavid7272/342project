package net.javaguides.__backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.InstructorDto;
import net.javaguides.__backend.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    // Create Instructor
    @PostMapping
    public ResponseEntity<InstructorDto> createInstructor(@RequestBody InstructorDto instructorDto) {
        InstructorDto savedInstructor = instructorService.createInstructor(instructorDto);
        return new ResponseEntity<>(savedInstructor, HttpStatus.CREATED);
    }

    // Get Instructor by ID
    @GetMapping("{id}")
    public ResponseEntity<InstructorDto> getInstructorById(@PathVariable("id") Long instructorId) {
        InstructorDto instructorDto = instructorService.getInstructorById(instructorId);
        return ResponseEntity.ok(instructorDto);
    }

    // Get All Instructors
    @GetMapping
    public ResponseEntity<List<InstructorDto>> getAllInstructors() {
        List<InstructorDto> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    // Update Instructor by ID
    @PutMapping("{id}")
    public ResponseEntity<InstructorDto> updateInstructor(@PathVariable("id") Long instructorId,
                                                          @RequestBody InstructorDto updatedInstructor) {
        InstructorDto instructorDto = instructorService.updateInstructor(instructorId, updatedInstructor);
        return ResponseEntity.ok(instructorDto);
    }

    // Delete Instructor by ID
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable("id") Long instructorId) {
        instructorService.deleteInstructor(instructorId);
        return ResponseEntity.noContent().build();
    }
}
